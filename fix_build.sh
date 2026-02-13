#!/bin/bash
set -e

echo "🔧 Fixing Android Build Environment..."

# 1. Ensure compatible Java (JDK 17)
# Try to find Homebrew OpenJDK 17
if [ -d "/opt/homebrew/opt/openjdk@17" ]; then
    export JAVA_HOME="/opt/homebrew/opt/openjdk@17"
    export PATH="$JAVA_HOME/bin:$PATH"
    echo "✅ Using OpenJDK 17 from Homebrew"
else
    echo "❌ OpenJDK 17 is required but not found."
    echo "👉 Please run: brew install openjdk@17"
    echo "   Then run this script again."
    exit 1
fi

# 2. Fix Gradle Wrapper
# Gradle 9.x (installed via brew) cannot configure this project directly due to API changes.
# We temporarily hide the build config to generate the wrapper cleanly.

if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    echo "📦 Fixing missing Gradle Wrapper..."
    
    # Backup
    mv build.gradle.kts build.gradle.kts.tmp
    mv settings.gradle.kts settings.gradle.kts.tmp
    touch settings.gradle.kts # Empty settings file
    
    # Generate wrapper for 8.9
    echo "🛠️  Generating Wrapper..."
    gradle wrapper --gradle-version 8.9
    
    # Restore
    rm settings.gradle.kts
    mv build.gradle.kts.tmp build.gradle.kts
    mv settings.gradle.kts.tmp settings.gradle.kts
    echo "✅ Wrapper generated."
fi

# 3. Build
echo "🚀 Building Debug APK..."
chmod +x gradlew
./gradlew assembleDebug

echo "✅ Build Successful!"
echo "📍 APK location: app/build/outputs/apk/debug/app-debug.apk"

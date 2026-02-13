# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# ─── Room ─────────────────────────────────────────────
-keep class com.example.memoreels.data.model.** { *; }
-keep class com.example.memoreels.data.local.** { *; }
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# ─── Hilt / Dagger ────────────────────────────────────
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent
-dontwarn dagger.internal.codegen.**

# ─── ML Kit (Image Labeling + Face Detection) ────────
-keep class com.google.mlkit.** { *; }
-dontwarn com.google.mlkit.**
-keep class com.google.android.gms.internal.mlkit_vision_label** { *; }
-keep class com.google.android.gms.internal.mlkit_vision_face** { *; }

# ─── Glance (Widgets) ────────────────────────────────
-keep class androidx.glance.** { *; }
-dontwarn androidx.glance.**

# ─── WorkManager + Hilt Worker ───────────────────────
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.ListenableWorker
-keep class androidx.hilt.work.** { *; }
-dontwarn androidx.hilt.work.**

# ─── Nearby Connections ──────────────────────────────
-keep class com.google.android.gms.nearby.** { *; }
-dontwarn com.google.android.gms.nearby.**

# ─── Coil ─────────────────────────────────────────────
-keep class coil.** { *; }
-dontwarn coil.**

# ─── Media3 / ExoPlayer ──────────────────────────────
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

# ─── Kotlin Serialization (if used) ──────────────────
-keepattributes *Annotation*
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}

# ─── General ──────────────────────────────────────────
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

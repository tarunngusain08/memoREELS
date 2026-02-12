#!/bin/sh

#
# Gradle start up script for POSIX
#

# Resolve links: $0 may be a link
app_path=$0
while [ -h "$app_path" ]; do
  ls=$(ls -ld "$app_path")
  link=${ls#*' -> '}
  case $link in
    /*) app_path=$link ;;
    *) app_path="$APP_HOME$link" ;;
  esac
done
APP_BASE_NAME=${0##*/}
APP_HOME=$(cd -P "${app_path%/*}" 2>/dev/null && pwd) || exit

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [ -n "$JAVA_HOME" ]; then
  JAVACMD=$JAVA_HOME/bin/java
else
  JAVACMD=java
fi

exec "$JAVACMD" \
  -Dorg.gradle.appname=$APP_BASE_NAME \
  -classpath "$CLASSPATH" \
  org.gradle.wrapper.GradleWrapperMain \
  "$@"

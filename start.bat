@echo off

call gradlew clean build --no-daemon --warning-mode=summary

set JAR_FILE=build/libs/cards-0.0.1-SNAPSHOT.jar
set JAVA_OPTS=-Xms512m -Xmx512m
set SPRING_PROFILE=dev

echo Starting Spring Boot application...
java %JAVA_OPTS% -jar %JAR_FILE% --spring.profiles.active=%SPRING_PROFILE%
pause

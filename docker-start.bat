@echo off

set IMAGE_NAME=cards-app
set CONTAINER_NAME=cards-app-container
set SPRING_PROFILE=dev
set HOST_PORT=8080
set CONTAINER_PORT=8080


echo Building Docker image %IMAGE_NAME%...
docker build -t %IMAGE_NAME% .


docker ps -a --format "{{.Names}}" | findstr /i %CONTAINER_NAME%

IF %ERRORLEVEL% EQU 0 (
    echo Stopping existing container %CONTAINER_NAME%...
    docker stop %CONTAINER_NAME%
    docker rm %CONTAINER_NAME%
)

echo Starting Docker container %CONTAINER_NAME% on port %HOST_PORT%...
docker run -d --name %CONTAINER_NAME% -p %HOST_PORT%:%CONTAINER_PORT% -e SPRING_PROFILES_ACTIVE=%SPRING_PROFILE% %IMAGE_NAME%

echo Attaching to logs. Press Ctrl+C to exit.
docker logs -f %CONTAINER_NAME%

# Cards Application

A Spring Boot application for managing accounts & transactions. 
Supports 
**Dev/Prod profiles**, 
**H2 in-memory DB for dev**, 
and can be run locally or via **Docker**.

---

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Running Locally](#running-locally)
- [Running with Docker](#running-with-docker)
- [Profiles](#profiles)
- [Configuration](#configuration)
- [Notes](#notes)

---

## Features

- REST APIs for account creation and transaction 
- transaction has both v1 - async(fire & forget) & v2 - sync
- Hibernate validator spring-boot-starter-validation as required
- Custom validators planned for account, transaction, documents to customize error handling messages.
- Mocked queue using BlockingQueue .
- Document upload for account verification: PDF, JPG, PNG; max size 10 MB
- Resumable / chunked file upload support (planned)
- Partial reactive implementation for doc upload
- r2dbc planned - full reactive implementation(planned) 
- Profiles for Dev and Prod
- Metrics via Spring Boot Actuator
- Dockerized for easy deployment

---

## Prerequisites

- Java 17+
- Gradle 8+ (optional if using gradlew)
- Docker (for containerized runs)
- Windows / Linux / Mac environment

---

## Project Structure

- build.gradle at root 
- src - java implementation 
- test - unit/integration tests
- script files to start app on docker or using jar at root

---
## Running Locally
1. Open **Command Prompt** in project root
2. Run the provided script:

```cmd
start.bat 
```

---
## Running with Docker
1. Open **Command Prompt** in project root
2. Run the provided script:
3. Set SPRING_PROFILES_ACTIVE to switch profile in script

```cmd
docker-start.bat
```

## Profiles
1. dev
2. prod
3. int

**Set profile via:**

-  --spring.profiles.active=dev
- -e SPRING_PROFILES_ACTIVE=dev
---

## Configuration

**Spring Actuator endpoints:**

- /actuator/health
- /actuator/info
- /actuator/metrics
- /actuator/prometheus

**Open API Spec:**

- http://localhost:8080/webjars/swagger-ui/index.html

--- 
## Notes

- Partial reactive 
- db calls blocking
- file upload reactive for better uploading and handle back pressure
- Queue implementation using blocking queue - multiple subscriber are allowed like a topic
- Queue - Concurrency - 1 as of now
FROM eclipse-temurin:17-jre
WORKDIR /cards
COPY build/libs/*.jar cards-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","cards-0.0.1-SNAPSHOT.jar"]
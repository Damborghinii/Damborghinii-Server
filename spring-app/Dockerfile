FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . /app
RUN gradle wrapper --gradle-version 8.5
RUN ./gradlew clean build -x test

FROM openjdk:21
COPY --from=builder /app/build/libs/dambo-0.0.1-SNAPSHOT.jar /dambo.jar
EXPOSE 8080
CMD ["java", "-jar", "/dambo.jar"]
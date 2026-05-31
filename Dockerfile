# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-slim AS builder

WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-slim

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

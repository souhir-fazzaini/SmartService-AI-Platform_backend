FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy project files
COPY . .

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew bootJar -x test

# Optional: Copy the built JAR to a final stage
FROM openjdk:17-jdk-slim
COPY --from=0 /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

# Step 1: Use a lightweight Java runtime as the base
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from your target folder to the container
COPY target/*.jar app.jar

# Step 4: Tell Docker to listen on port 8761
EXPOSE 8761

# Step 5: Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
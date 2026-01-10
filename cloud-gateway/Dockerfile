# Step 1: Use a lightweight Java runtime as the base
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file
COPY target/*.jar app.jar

# Step 4: Tell Docker the container listens on 8080 (Gateway port)
EXPOSE 8080

# Step 5: Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
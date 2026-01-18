# Step 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app
# Copy only the pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Create the runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# This line is the most important - it grabs the JAR you just built
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
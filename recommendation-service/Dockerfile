# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first (cached layer)
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final lightweight image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar recommendation-service.jar

# Set the active profile if needed
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "recommendation-service.jar"]

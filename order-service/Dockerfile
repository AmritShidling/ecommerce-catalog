# --- STEP 1: Build Stage ---
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Cache dependencies: only copy pom.xml first
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and compile
COPY src ./src
RUN mvn clean package -DskipTests

# --- STEP 2: Runtime Stage ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Security: Create a non-root user to run the app
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copy only the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Environment defaults
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
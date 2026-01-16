# --- STEP 1: Build Stage ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# --- STEP 2: Runtime Stage ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring
COPY --from=build /app/target/catalog-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
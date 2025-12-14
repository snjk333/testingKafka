# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy pom files
COPY pom.xml .
COPY common-lib/pom.xml common-lib/
COPY kafkaMailProducer/pom.xml kafkaMailProducer/

# Copy source code
COPY common-lib/src common-lib/src
COPY kafkaMailProducer/src kafkaMailProducer/src

# Build the project
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /build/kafkaMailProducer/target/*.jar app.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Stage 1: Build the application
FROM maven:3.8.1-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: Build the final image
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/your-app.jar ./app.jar

# Install MongoDB client (if needed)
# RUN apt-get update && apt-get install -y mongodb-clients

# Command to run the application
CMD ["java", "-jar", "app.jar"]
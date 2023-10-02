# Use an official OpenJDK image as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY ./projet_infra_3_backend/target/projet_infra_3_backend-0.0.1-SNAPSHOT.jar /app/

# Expose port 8080
EXPOSE 8080

# Additional setup for development (example: installing curl)
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Argument to specify build environment (default to 'dev')
ARG BUILD_ENV=dev

# Command to run the application based on the build environment
CMD ["java", "-jar", "projet_infra_3_backend-0.0.1-SNAPSHOT.jar"]
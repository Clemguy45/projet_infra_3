# Utiliser une image OpenJDK officielle comme image parent
FROM openjdk:11-jre-slim

# Définir le répertoire de travail à /app
WORKDIR /app

# Installer apt-utils pour résoudre le problème "debconf: delaying package configuration"
RUN apt-get update && apt-get install -y apt-utils && rm -rf /var/lib/apt/lists/*

# Copier les fichiers du répertoire target de votre projet dans le conteneur à /app/
COPY ./projet_infra_3_backend/target/projet_infra_3_backend-0.0.1-SNAPSHOT.jar /app/

# Exposer le port 8080 (port par défaut pour une application Spring Boot)
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "projet_infra_3_backend-0.0.1-SNAPSHOT.jar"]

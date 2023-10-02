# Use an official Node runtime as a parent image
FROM node:14

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app/

# Install any needed packages specified in package.json
RUN npm install

# Expose port 80
EXPOSE 80

# Command to run the application
CMD ["npm", "start", "ng", "serve"]

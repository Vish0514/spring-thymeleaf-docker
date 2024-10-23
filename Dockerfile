# Use a Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Use a new image for the runtime
FROM openjdk:17-jdk

# Set the maintainer label
LABEL maintainer="pateluday07@gmail.com"

# Create a volume for temporary files
VOLUME /tmp

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/docker-mysql-springboot-demo-0.0.1-SNAPSHOT.jar docker-mysql-springboot-demo-0.0.1-SNAPSHOT.jar

# Copy the wait-for-mysql script
COPY wait-for-mysql.sh /wait-for-mysql.sh

# Make the script executable
RUN chmod +x /wait-for-mysql.sh

# Set the entrypoint to the wait-for-mysql script
ENTRYPOINT ["/wait-for-mysql.sh"]

# Set the default command to run the jar file
CMD ["java","-jar","/docker-mysql-springboot-demo-0.0.1-SNAPSHOT.jar"]
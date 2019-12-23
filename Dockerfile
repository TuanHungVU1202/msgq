# Start with a base image containing Java runtime
FROM openjdk:8

# Add Maintainer Info
LABEL maintainer="john.doan@contemi.com.vn"

# Make port 8081 available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=target/msgq-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} search-java-service.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/search-java-service.jar"]

#base image
FROM ubuntu:18.04
# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get clean;

# Copy .jar file
RUN mkdir /data
COPY build/libs/MOI-1.0-SNAPSHOT.jar /data
WORKDIR /data
CMD java -jar MOI-1.0-SNAPSHOT.jar

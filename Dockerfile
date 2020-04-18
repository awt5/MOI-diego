#base image
FROM moi-ubase:1.0

# Copy .jar file
RUN mkdir /data
COPY build/libs/MOI-1.0-SNAPSHOT.jar /data
WORKDIR /data
CMD java -jar MOI-1.0-SNAPSHOT.jar

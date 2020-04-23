#base image
FROM jdiego13/moi-ubase:1.0

# Copy .jar file
RUN mkdir /data
COPY build/libs/MOI-1.0-*.jar /data
WORKDIR /data
CMD java -jar MOI-1.0-*.jar

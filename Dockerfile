FROM adoptopenjdk/openjdk11:latest

COPY ims-backend/build/libs/*.jar /opt/backend/bin/app.jar
WORKDIR /opt/backend/bin/
CMD java -jar app.jar

EXPOSE 8089

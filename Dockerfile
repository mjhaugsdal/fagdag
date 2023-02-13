FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
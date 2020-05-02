FROM openjdk:11-jre
ARG JAR_FILE
EXPOSE 8080
ADD $JAR_FILE app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
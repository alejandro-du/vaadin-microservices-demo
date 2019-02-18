FROM openjdk:8-jre-alpine
ARG JAR_FILE
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Xmx128m", "-jar", "app.jar", "--server.port=8080"]
EXPOSE 8080

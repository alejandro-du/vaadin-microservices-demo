FROM openjdk:8-jre-alpine
ARG JAR_FILE
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar", "app.jar", "--server.port=8201"]
EXPOSE 8201

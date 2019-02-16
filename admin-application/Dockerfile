FROM openjdk:8-jre-alpine
ARG JAR_FILE
COPY target/${JAR_FILE} app.jar
CMD java -jar -Xmx128m -Dserver.port=8080 -Deureka.client.serviceUrl.defaultZone=http://discovery-server:8080/eureka/ app.jar
EXPOSE 8080

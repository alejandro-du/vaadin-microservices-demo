# Microservices with Vaadin demo

This demo shows a Microservices Architecture implemented with [Spring Cloud Netflix](http://cloud.spring.io/spring-cloud-netflix/) and [Vaadin](https://vaadin.com).

## Building the demo

Run the following from the command line:
```
git clone https://github.com/alejandro-du/vaadin-microservices-demo.git
cd vaadin-microservices-demo
mvn package
```

## Running the demo

Use multiple (seven) terminals to perform the following steps:

**1) Start the `discovery-server` application (Eureka app):**
```
cd vaadin-microservices-demo/discovery-server
java -jar target/discovery-server-0.0.1-SNAPSHOT.jar
```

**2) Start the `config-server` application (Spring Cloud Config app):**
```
cd vaadin-microservices-demo/config-server
java -jar target/config-server-0.0.1-SNAPSHOT.jar
```

**3) Start two instances of the `biz-application` microservice (REST app):**
```
cd vaadin-microservices-demo/biz-application
java -Dserver.port=8001 -jar target/biz-application-0.0.1-SNAPSHOT.jar
```
```
cd vaadin-microservices-demo/biz-application
java -Dserver.port=8002 -jar target/biz-application-0.0.1-SNAPSHOT.jar
```

**4) Start two instances of the `admin-application` microservice (Vaadin app):**
```
cd vaadin-microservices-demo/admin-application
java -Dserver.port=9001 -jar target/admin-application-0.0.1-SNAPSHOT.jar
```
```
cd vaadin-microservices-demo/admin-application
java -Dserver.port=9002 -jar target/admin-application-0.0.1-SNAPSHOT.jar
```

**5) Start the `proxy-server` application (Zuul app):**
```
cd vaadin-microservices-demo/proxy-server
java -jar target/proxy-server-0.0.1-SNAPSHOT.jar
```

## Using the demo

**1) Point your browser to <http://localhost:8080>.**

This is the "edge service" implemented with Netflix Zuul. It acts as a reverse proxy, redirecting requests to the `admin-application` instances using a load balancer provided by Netflix Ribbon and a custom _sticky session_ rule.

If you get a "Server not available" message, please wait until all the services are registered with the `discovery-server` (implemented with Netflix Eureka).

**2) Add, update, or delete data.**

The `admin-application `(implemented with Vaadin) will delegate the CRUD operations to the `biz-application` (implemented with Spring Data Rest) using a load balancer, provided by Netflix Ribbon, with a _round robin_ strategy.

**3) Add or remove microservice instances.**

You can horizontally scale the system by starting more instances of the `biz-application` and `admin-application` microservices. Remember to specify a distinct port (using `-Dserver.port=NNNN`) when you start a new instance.

**4) Test high-availability.**

Refresh the browser to generate some log output. Check the logs in the `admin-application` instances to find the instance currently serving the webapp and kill the process.
Go back to the browser and click a row in the Grid. You'll see a "Please wait..." message and after awhile you should be able to continue using the webapp without losing the state thanks to the externalized HTTP Session with Spring Session and Hazelcast.

**5 Test system resilience.**

Stop all the instances of the `biz-application` microservice to see the fallback mechanism (implemented with Netflix Hystrix).

## Developing

You don't need to have all the infrastructure services running (`discovery-server`, `config-server`, and `proxy-server`) in order to develop one of the microservices (`biz-application`, `admin-application`).
Activate the `development` Spring profile to use a local configuration (`application-development.properties) that excludes external orchestration services.

To develop the `biz-application` microservice you can run:
  
cd vaadin-microservices-demo/biz-application

```
java -Dspring.profiles.active=development -jar target/biz-application-0.0.1-SNAPSHOT.jar
```

The `admin-application` needs a the REST web-service provided by the `biz-application`. You can either, run the `biz-application` in `development` mode or create a _mock_ REST web service. You can configure the end point with the `biz-application.url` property in the `application-development.properties` file of the `admin-application` microservice.
# Microservices with Vaadin demo

This demo shows a microservices architecture implemented with [Spring Cloud Netflix](http://cloud.spring.io/spring-cloud-netflix/) and [Vaadin](https://vaadin.com).

## Building the demo

Run the following from the command line:
```
git clone https://github.com/alejandro-du/vaadin-microservices-demo.git
cd vaadin-microservices-demo
mvn package
```

## Running the demo

Use multiple (six) terminals to perform the following steps:

**1) Start the `discovery-server` application (Eureka app):**
```
cd vaadin-microservices-demo/discovery-server
java -jar target/discovery-server-0.0.1-SNAPSHOT.jar
```

**2) Start two instances of the `biz-application` microservice (REST app):**
```
cd vaadin-microservices-demo/biz-application
java -jar target/biz-application-0.0.1-SNAPSHOT.jar
```
```
cd vaadin-microservices-demo/biz-application
java -Dserver.port=8002 -jar target/biz-application-0.0.1-SNAPSHOT.jar
```

**3) Start two instances of the `admin-application` microservice (Vaadin app):**
```
cd vaadin-microservices-demo/admin-application
java -jar target/admin-application-0.0.1-SNAPSHOT.jar
```
```
cd vaadin-microservices-demo/admin-application
java -Dserver.port=8003 -jar target/admin-application-0.0.1-SNAPSHOT.jar
```

**4) Start the `proxy-server` application (Zuul app):**
```
cd vaadin-microservices-demo/proxy-server
java -jar target/proxy-server-0.0.1-SNAPSHOT.jar
```

## Using the demo

**1) Point your browser to <http://localhost:8080>.**

This is the "edge service" implemented with Netflix Zuul. It acts as a reverse proxy, redirecting requests to the `admin-application` instances using a load balancer provided by Netflix Ribbon and a custom sticky session rule.

If you get a "Server not available" message, please wait until all the services are registered with the `discovery-server` (implemented with Netflix Eureka).

**2) Add, update, or delete data.**

The `admin-application `(implemented with Vaadin) will delegate the CRUD operations to the `biz-application` (implemented with Spring Data Rest) using a load balancer, provided by Netflix Ribbon, with a "round robin" strategy.

**3) Add or remove microservice instances.**

You can horizontally scale the system by starting more instances of the `biz-application` and `admin-application` microservices. Remember to specify different ports (using `-Dserver.port=NNNN`) when starting new instances.

**4) Test system resilience.**

Stop all the instances of the `biz-application` microservice to see the fallback mechanisim, implemented with Netflix Hystrix.

Stop one of the `admin-application` instances that you are sure is in use, and see the error message ("Server not available"). If there are more instances of the `admin-application`, you should be able to refresh the browser and get the connection to a different instance after some seconds/minutes.

## Implementing the demo

In the next days, I'll publish a guide with all the steps to develop this same demo by yourself. [Stay tunned](https://twitter.com/alejandro_du)!

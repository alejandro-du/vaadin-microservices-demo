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

**3) Start an instance of the `biz-application` microservice (REST app):**
```
cd vaadin-microservices-demo/biz-application
java -Dserver.port=9601 -jar target/biz-application-0.0.1-SNAPSHOT.jar
```

**4) Start an instance of the `admin-application` microservice (Vaadin app):**
```
cd vaadin-microservices-demo/admin-application
java -Dserver.port=9401 -jar target/admin-application-0.0.1-SNAPSHOT.jar
```

**5) Start an instance of the `news-application` microservice (Vaadin app):**
```
cd vaadin-microservices-demo/news-application
java -Dserver.port=9201 -jar target/news-application-0.0.1-SNAPSHOT.jar
```

**6) Start an instance of the `website-application` microservice (Vaadin app):**
```
cd vaadin-microservices-demo/website-application
java -Dserver.port=9001 -jar target/website-application-0.0.1-SNAPSHOT.jar
```

**7) Start the `proxy-server` application (Zuul app):**
```
cd vaadin-microservices-demo/proxy-server
java -jar target/proxy-server-0.0.1-SNAPSHOT.jar
```

## Using the demo

**1) Point your browser to <http://localhost:8080>.**

You'll see the `website-application` embedding the `admin-application` and the `news-application` microservices.

This is the "edge service" implemented with Netflix Zuul. It acts as a reverse proxy, redirecting requests to the `website-application`, `news-application`, and `admin-application` instances using a load balancer provided by Netflix Ribbon with a _round robin_ strategy.

If you get a "Server not available" message, please wait until all the services are registered with the `discovery-server` (implemented with Netflix Eureka).

**2) Add, update, or delete data.**

Latest tweets from the companies you enter on the left (the `admin-application`) will be rendered on the right (the `news-application`).

The `admin-application`, and `news-application` instances (implemented with Vaadin) delegate CRUD operations to the `biz-application` (implemented with Spring Data Rest) using a load balancer (provided by Netflix Ribbon) with a _round robin_ strategy.

**3) Add microservice instances.**

You can horizontally scale the system by starting more instances of the `biz-application`, `admin-application`, `news-application`, and `website-application` microservices. Remember to specify an available port (using `-Dserver.port=NNNN`) when you start a new instance.

**4) Test high-availability.**

Make sure you are running two instances of the `admin-application`. Click the _+_ (Add) button and enter `Vaadin`
as the _name_, and `vaadin` as the _Twitter Username_. Don't click the _Add_ button yet.

Stop one of the instences of the `admin-application` and click the _Add_ button. The web application should remain functional and save the data you entered without losing the state of the UI thanks to the externalized HTTP Session (implemented with Spring Session and Hazelcast).

**5) Test system resilience.**

Stop all the instances of the `biz-application` microservice and refresh the browser to see the fallback mechanisms (implemented with Netflix Hystrix) in the `admin-application` and `news-application` microservices.

## Developing

You don't need to have all the infrastructure services running (`discovery-server`, `config-server`, and `proxy-server`) in order to develop individual microservices (`biz-application`, `admin-application`, `news-application`, and `website-application`). Activate the `development` Spring profile to use a local configuration (`application-development.properties`) that excludes external orchestration services.

For example, during development you can run the `biz-application` microservice using:

```
cd vaadin-microservices-demo/biz-application
java -Dspring.profiles.active=development -jar target/biz-application-0.0.1-SNAPSHOT.jar
```


With the `admin-application`, and `news-application` you need the REST web-service provided by the `biz-application`. You can either, run the `biz-application` in `development` mode or create a _mock_ REST web service. You can configure the end point with the `biz-application.url` property in the `application-development.properties`.

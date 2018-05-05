# Prime Number generator


This sample application shows how to write a RESTful service with Spring Boot.

## Overview

This application exposes an endpoint in <http://127.0.0.1:9000/primes> that can be used to retrieve all prime numbers up to the given value.

The API is documented with [Swagger](http://swagger.io/) and the UI can be accessed on <http://127.0.0.1:9000/swagger-ui.html>.

The Spring Actuator can be accessed on <http://127.0.0.1:9001/actuator>.

The application also exposes a series of metrics that can be retrieved using a JMX console.

## How to compile

Just run the following command on the project root:

    ./mvnw clean package

To execute the binary in the shell type:

    java -jar ./prime-number-service-ws/target/prime-number-service-ws-0.0.1-SNAPSHOT.jar



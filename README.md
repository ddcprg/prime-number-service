# Prime Number generator

[![Build Status](https://travis-ci.org/ddcprg/prime-number-service.svg?branch=master)](https://travis-ci.org/ddcprg/prime-number-service) [![Coverage Status](https://coveralls.io/repos/github/ddcprg/prime-number-service/badge.svg?branch=master)](https://coveralls.io/github/ddcprg/prime-number-service?branch=master) ![GitHub license](https://img.shields.io/github/license/ddcprg/prime-number-service.svg)


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

    java -jar ./prime-number-service-ws/target/prime-number-service-ws-0.1.2-SNAPSHOT.jar

## Hit the endpoint

In order to access the service you must hit the endpoint `http://127.0.0.1:9000/primes/{number}` where `number` is the value up to which you want to generate prime numbers. You can also add the query parameter `algorithm` to choose from the set of primer number verification algorithms available:

  * Brute Force: this is the most basic and inefficient algorithm available which has been delivered with version _0.0.1_. Request `http://127.0.0.1:9000/primes/{number}?algorithm=bruteForce` if you want to use this algorithm.
  * Heuristic: this is slightly better and more efficient than the brute force algorithm and is the default implementations as of version _0.1.0_. available which has been delivered with version 0.0.1. Request `http://127.0.0.1:9000/primes/{number}?algorithm=heuristic` or just `http://127.0.0.1:9000/primes/{number}` if you want to use this algorithm.

## Docker image

You can build a Docker image by activating the Maven profile `docker`. This profile will build and tag the image, during the Maven `package` phase, and push it, during the Maven `deploy` phase, to the local Docker Registry using the project version to tag the image. Make sure you have Docker installed in your compiling machine.

To run the image issue this command:

    docker run -p 127.0.0.1:$HOST_PORT:9000 --name $CONTAINER_NAME -t company/prime-number-service-docker:0.1.2-SNAPSHOT

This will create a new Docker container with name `$CONTAINER_NAME`which exposes the local port `$HOST_PORT`.

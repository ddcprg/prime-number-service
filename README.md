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

    java -jar ./prime-number-service-ws/target/prime-number-service-ws-0.0.1-SNAPSHOT.jar



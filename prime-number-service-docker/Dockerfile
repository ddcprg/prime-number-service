FROM openjdk:8-jdk-alpine
VOLUME /tmp/prime-number-service
ARG JAR_FILE
COPY ${JAR_FILE} prime-number-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/prime-number-service.jar"]

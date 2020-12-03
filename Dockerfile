FROM openjdk:8-jre-alpine

WORKDIR /

RUN mkdir app
COPY target/demoPrueba.jar /app
WORKDIR /app

#Exponemos el puerto 8080
EXPOSE 8080


CMD ["java","-jar","demoPrueba-0.0.1-SNAPSHOT.jar"]
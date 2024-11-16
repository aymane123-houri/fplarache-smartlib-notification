FROM openjdk:18
VOLUME /tmp
COPY target/*.jar notification-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notification-service.jar"]

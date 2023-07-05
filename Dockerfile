FROM openjdk:21
MAINTAINER lenskyi
COPY target/javaspring.lenskyi-hogwarts-schedule-manager.jar hogwarts.jar
ENTRYPOINT ["java", "-jar", "hogwarts.jar"]
EXPOSE 8080
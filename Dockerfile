FROM adoptopenjdk:11-jre
EXPOSE 8080
ADD target/recipes-1.0.0.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
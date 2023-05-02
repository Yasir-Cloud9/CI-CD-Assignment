FROM openjdk:18
COPY target/Online-Food-Order-Application-0.0.1-SNAPSHOT.jar /usr/local/app/
WORKDIR /usr/local/app
EXPOSE 8090
CMD ["java", "-jar", "Online-Food-Order-Application-0.0.1-SNAPSHOT.jar"]
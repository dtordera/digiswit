FROM openjdk:17

EXPOSE 9001

COPY target/digiswit-latest.jar /digiswit-latest.jar

CMD ["java", "-jar", "digiswit-latest.jar", "--server.port=9001"]

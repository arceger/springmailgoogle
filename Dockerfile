FROM eclipse-temurin:20-jdk-jammy
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
CMD ["java", "-jar","/app.jar"]


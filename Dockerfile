FROM gradle:7.6.1-jdk11
COPY . .
EXPOSE 8080
RUN gradle build
ENTRYPOINT ["java", "-jar", "build/libs/HomeBanking-0.0.1-SNAPSHOT.jar"]

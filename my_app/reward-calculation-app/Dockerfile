FROM eclipse-temurin:21

COPY build/libs/reward-calculation-app-1.0.2.jar reward-calculation-app.jar

CMD ["java", "-Dspring.profiles.active=mysql-container", "-jar", "reward-calculation-app.jar"]

EXPOSE 8080

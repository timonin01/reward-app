FROM eclipse-temurin:21

COPY build/libs/reward-payment-app-1.0.2.jar reward-payment-app.jar

CMD ["java", "-Dspring.profiles.active=mysql-container", "-jar", "reward-payment-app.jar"]

EXPOSE 8090

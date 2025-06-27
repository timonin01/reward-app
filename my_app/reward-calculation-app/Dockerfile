FROM eclipse-temurin:21

# Install bash
# RUN apk add --no-cache bash

# Set the working directory inside the container
WORKDIR /app

COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

COPY build/libs/reward-calculation-app-1.0.2.jar /reward-calculation-app.jar

CMD ["/app/wait-for-it.sh", "reward-calculation-db:3306", "--timeout=30", "--", "java", "-jar", "$APP_JAR"]

EXPOSE 8080

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY .env .
COPY build.gradle .
COPY gradlew .
COPY gradlew.bat .
COPY mvp.service .
COPY settings.gradle .
COPY src src
COPY gradle gradle

RUN chmod +x gradlew
CMD ["./gradlew", "run"]

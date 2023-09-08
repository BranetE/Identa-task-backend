FROM eclipse-temurin:17-jdk-alpine
LABEL authors="oleh_kulbaba"

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

ENTRYPOINT ["./mvnw", "spring-boot:run"]
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

WORKDIR /app

COPY target/CarREST-0.0.1-SNAPSHOT.jar /app/CarREST-0.0.1-SNAPSHOT.jar

ARG DEPENDENCY=target/dependency

LABEL authors="vadympskn"

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app


ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/carrest \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=1111

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "CarREST-0.0.1-SNAPSHOT.jar"]

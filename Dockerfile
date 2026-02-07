# Stage 1: Build
FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/dailymood-*.jar app.jar

EXPOSE 6969

ENTRYPOINT ["java", "-jar", "app.jar"]

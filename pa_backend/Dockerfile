# Build stage
FROM maven:3.9.4 AS build

WORKDIR /app

COPY . .

RUN mvn clean package

# Run stage
FROM openjdk:20

COPY --from=build /app/target/paPlatypusBackend-0.0.1-SNAPSHOT.jar /usr/local/lib/paPlatypusBackend-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/usr/local/lib/paPlatypusBackend-0.0.1-SNAPSHOT.jar"]

EXPOSE 8000

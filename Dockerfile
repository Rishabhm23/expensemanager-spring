# ---------- Stage 1: Build the JAR ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run the JAR ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built JAR from the build stage
COPY --from=build /app/target/expensemanager-0.0.1-SNAPSHOT.jar expensemanager-v1.0.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "expensemanager-v1.0.jar"]


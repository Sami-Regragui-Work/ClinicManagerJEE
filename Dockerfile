# Stage 1: Build with Maven (docker profile)
FROM maven:3.9-eclipse-temurin-25 as builder

WORKDIR /build
COPY . .

# Build with docker profile (uses postgres:5432 host)
RUN mvn clean package -DskipTests

# Stage 2: Runtime (Tomcat)
FROM tomcat:10-jdk25-temurin

# Copy built WAR from builder stage
COPY --from=builder /build/target/Clinic-Manager-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/clinic-manager.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
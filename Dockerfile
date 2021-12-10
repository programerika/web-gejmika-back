FROM balenalib/aarch64-openjdk as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package spring-boot:repackage

FROM balenalib/aarch64-openjdk
COPY --from=builder app/target/*.jar web-gejmika-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "web-gejmika-back.jar"]

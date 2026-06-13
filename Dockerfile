# Build WAR ở stage riêng, runtime chỉ cần JRE để image deploy gọn hơn.
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /workspace

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw && ./mvnw -B -DskipTests dependency:go-offline

COPY src ./src

RUN ./mvnw -B -DskipTests clean package

FROM eclipse-temurin:17-jre-focal

WORKDIR /app

ENV PORT=8080
EXPOSE 8080

COPY --from=build /workspace/target/ecommerce_project-0.0.1-SNAPSHOT.war app.war

ENTRYPOINT ["java", "-jar", "/app/app.war"]

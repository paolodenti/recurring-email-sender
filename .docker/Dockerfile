ARG CORRETTO_VERSION=21.0.7

FROM amazoncorretto:${CORRETTO_VERSION} AS build
WORKDIR /app
COPY . .
RUN ./mvnw -s settings.xml --no-transfer-progress clean package -DskipTests=true -Dcheckstyle.skip=true -P-test

FROM amazoncorretto:${CORRETTO_VERSION}
WORKDIR /app
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

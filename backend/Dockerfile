FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

ARG BACKEND_DIR=/CapybaraCampusCrawlBackend
COPY ${BACKEND_DIR}/mvnw .
COPY ${BACKEND_DIR}/.mvn .mvn
COPY ${BACKEND_DIR}/pom.xml .
COPY ${BACKEND_DIR}/src src

RUN sed -i 's/\r$//' mvnw
RUN ./mvnw install -DskipTests

RUN java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG EXTRACTED=/workspace/app/target/extracted
RUN true


COPY --from=build ${EXTRACTED}/dependencies/ ./
RUN true

COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
RUN true

COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
RUN true

COPY --from=build ${EXTRACTED}/application/ ./
RUN true

COPY ./docker-config/application.properties ./config/application.properties
RUN true

COPY ./docker-config/openrouteservice.properties ./config/openrouteservice.properties
RUN true

RUN cat ./config/application.properties ./config/openrouteservice.properties > ./config/combined.properties
RUN rm ./config/application.properties ./config/openrouteservice.properties
RUN mv ./config/combined.properties ./config/application.properties

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher", "-spring.config.location=", "./config/"]
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

ARG DATABASE_STRING=jdbc:postgresql://localhost:5432/capybara_db
ARG DATABASE_USER=capy 
ARG DATABASE_PASSWD=capy
ARG ORS_KEY=API_KEY

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

RUN printf "spring.datasource.url=${DATABASE_STRING}\n" >> ./config/database.properties
RUN printf "spring.datasource.username=${DATABASE_USER}\n" >> ./config/database.properties
RUN printf "spring.datasource.password=${DATABASE_PASSWD}\n" >> ./config/database.properties

RUN printf "openrouteservice.api.key=${ORS_KEY}\n" >> ./config/openrouteservice.properties
RUN printf "openrouteservice.active=true\n" >> ./config/openrouteservice.properties

RUN cat ./config/database.properties ./config/application.properties > ./config/databaseapp.properties
RUN cat ./config/databaseapp.properties ./config/openrouteservice.properties > ./config/combined.properties
RUN rm ./config/application.properties ./config/openrouteservice.properties ./config/database.properties
RUN mv ./config/combined.properties ./config/application.properties


ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher", "-spring.config.location=", "./config/, -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"]
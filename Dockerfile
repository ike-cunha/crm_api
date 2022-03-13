#https://docs.docker.com/develop/develop-images/multista ge-build/
FROM quay.io/quarkus/ubi-quarkus-native-image:22.0.0-java17 AS build
COPY --chown=quarkus:quarkus . /opt/quarkus-src
USER quarkus
WORKDIR /opt/quarkus-src/crm-api
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
RUN ./mvnw -B package

FROM  quay.io/quarkus/ubi-quarkus-native-image:22.0.0-java17 AS runtime
COPY --from=build --chown=quarkus:quarkus /opt/quarkus-src/crm-api/target/quarkus-app /opt/quarkus-app/
EXPOSE 13372
ENTRYPOINT ["java","-jar","/opt/quarkus-app/quarkus-run.jar"]

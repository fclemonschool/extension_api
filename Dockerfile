FROM gradle:jdk-alpine AS builder
RUN java -version

COPY . /usr/src/extension/
WORKDIR /usr/src/extension/
RUN gradle build --no-daemon

FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=builder /usr/src/extension/build/libs/extension-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./app.jar"]

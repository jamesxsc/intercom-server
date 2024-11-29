FROM openjdk:17-jdk-alpine AS builder
ARG jarfile=build/libs/*.jar
COPY ${jarfile} intercom.jar
RUN java -Djarmode=layertools -jar intercom.jar extract
FROM openjdk:17-jdk-alpine
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
EXPOSE 4001:4001
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

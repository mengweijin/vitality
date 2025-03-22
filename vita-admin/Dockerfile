FROM openjdk:17.0.2-oraclelinux8
LABEL authors="mengweijin"

RUN mkdir -p /opt/vita/logs \
    /opt/vita/temp

WORKDIR /opt/vita

ENV SERVER_PORT=8080 LANG=C.UTF-8 LC_ALL=C.UTF-8

EXPOSE ${SERVER_PORT}
ARG JAR_FILE

ADD ./target/${JAR_FILE} ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            "-jar", "app.jar"]

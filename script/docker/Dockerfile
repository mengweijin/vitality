#FROM findepi/graalvm:java17-native
FROM openjdk:17.0.2-oraclelinux8

LABEL maintainer="Meng Wei Jin"

ARG DIR=/opt/vitality

RUN mkdir -p ${DIR}

WORKDIR ${DIR}

ENV SERVER_PORT=8080 LANG=C.UTF-8 LC_ALL=C.UTF-8 JAVA_OPTS=""

EXPOSE ${SERVER_PORT}

#ADD ./target/vitality-admin.jar ./vitality.jar

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -Dserver.port=${SERVER_PORT} \
           ${JAVA_OPTS} \
           -jar vitality-admin.jar
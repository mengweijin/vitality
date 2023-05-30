FROM java:8
MAINTAINER Meng Wei Jin mengweijin.work@foxmail.com

ARG JAR_FILE=${ARTIFACT_ID}-${VERSION}.jar

RUN mkdir /app
COPY \${JAR_FILE} /app/application.jar

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
EXPOSE 8080

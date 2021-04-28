FROM java:8
MAINTAINER Meng Wei Jin mengweijin.work@foxmail.com

ARG JAR_ARTIFACT_ID='sample-quickboot-mybatis-plus'
ARG JAR_VERSION='0.0.1-SNAPSHOT'
ARG JAR_NAME=${JAR_ARTIFACT_ID}-${JAR_VERSION}.jar

RUN mkdir /app
COPY target/${JAR_NAME} /app/application.jar

RUN bash -c 'touch /app/application.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/application.jar"]
EXPOSE 8080

# docker build -t sample-quickboot-mybatis-plus:latest ./sample-quickboot-mybatis-plus
# docker build -t registry.cn-hangzhou.aliyuncs.com/mengweijin/sample-quickboot-mybatis-plus:0.0.1.1-SNAPSHOT ./sample-quickboot-mybatis-plus

# docker login --username=***@**.com registry.cn-hangzhou.aliyuncs.com
# docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/mengweijin/sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT
# docker push registry.cn-hangzhou.aliyuncs.com/mengweijin/sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT

# docker run --name relx-club-invoicing-management-system -d -p 8080:8080 registry.cn-hangzhou.aliyuncs.com/mengweijin/sample-quickboot-mybatis-plus:latest

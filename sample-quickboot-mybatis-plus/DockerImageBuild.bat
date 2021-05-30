@echo off

call DockerImageDelete.bat

echo Building docker image

cd C:\Source\code\gitee\quickboot\sample-quickboot-mybatis-plus
docker build -t sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT .

rem docker login --username=***@**.com registry.cn-hangzhou.aliyuncs.com
rem docker tag sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT registry.cn-hangzhou.aliyuncs.com/mengweijin/sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT
rem docker push registry.cn-hangzhou.aliyuncs.com/mengweijin/sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT


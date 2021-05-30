@echo off

call DockerImageBuild.bat

echo Starting docker container

docker run --name sample-quickboot-mybatis-plus -d -p 8080:8080 sample-quickboot-mybatis-plus:0.0.1-SNAPSHOT

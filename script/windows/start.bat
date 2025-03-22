rem 使用者应根据自身平台编码自行转换 防止乱码 例如 win使用gbk编码
@echo off
title=Vita-Admin

chcp 65001 > nul

echo  启动倒计时（单位：秒）：
timeout /T 10 /NOBREAK

rem jar平级目录
set AppName=vita-admin.jar

rem JVM参数
set JVM_OPTS="-Dname=%AppName% -Dserver.port=8080 -Dspring.profiles.active=h2 -Dfile.encoding=utf-8 -Duser.timezone=Asia/Shanghai -Xms128m -Xmx512m"

for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %AppName%`) do (
	set pid=%%a
	set image_name=%%b
)
if  defined pid (
	echo %%is running
	PAUSE
)

echo  starting……
java %JVM_OPTS% -jar %AppName%
PAUSE


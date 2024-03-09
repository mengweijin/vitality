#!/bin/sh
APP_HOME=/opt/vitality
APP_NAME=app.jar

procnum=`ps -ef|grep $APP_NAME|grep -v grep|wc -l`
if [ $procnum -eq 0 ]
then
    cd $APP_HOME
    source /etc/profile
    mkdir -p logs
    echo `date +%Y-%m-%d` `date +%H:%M:%S` "restart $APP_NAME" >>$APP_HOME/logs/restart.log
    # 如果启动脚本有参数，同理，此处加上即可
    ./app.sh restart
fi
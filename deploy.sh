#!/bin/sh

pid=`pgrep -f java`
if [ $pid ]
then
    kill -9 $pid;
    echo '$pid process kill complete'
else
    echo 'pid is empty'
fi

sleep 5

nohup java -jar build/fire_inform-0.0.1-SNAPSHOT.jar &

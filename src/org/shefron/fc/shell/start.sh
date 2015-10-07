#!/bin/sh
CLASSPATH=$CLASSPATH:.
export CLASSPATH

echo $CLASSPATH

echo 'java调用shell脚本，并取得相应结果！'
echo $yudy
echo $shefron
echo '错误啦'

ps -ef | grep dc

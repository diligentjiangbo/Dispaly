#!/bin/sh

x=`ps aux | grep 'python stats.py' | grep -v grep | awk '{print $2}'`

if [ ${x} ] 
then 
  echo 'running'
else 
  ip=`ifconfig | grep inet | grep -v 127.0.0.1 | awk '{print $2}'`
  python stats.py stats.log $ip
fi
#!/bin/sh

ip=`ifconfig | grep inet | grep -v 127.0.0.1 | awk '{print $2}'`

python stats.py stats.log $ip
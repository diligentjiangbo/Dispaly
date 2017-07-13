#!/usr/bin/python
# -*- coding: UTF-8 -*-

import os
import re
import sys
import json
import time
import socket
import urllib2

MATCH_STR1 = ".*GROUP_GET_NUMS.*|.*TOPIC_PUT_NUMS.*|.*BROKER_PUT_NUMS.*|.*BROKER_GET_NUMS.*"
MATCH_STR2 = ".*Stats In One Minute.*"

TIME_FORMAT = '%Y-%m-%d %H:%M:%S.%f'

LOCAL_IP = ""

URL = "http://localhost:8080/python_demo"

TCP_HOST = '127.0.0.1'
TCP_PORT = 1234
TCP_CONNECTION = ''


#请求对象
class Request:
  #此对象只是为了方便构造json字符串
  #构造函数
  def __init__(self):
    self.channelId_ = "6020102"
    self.logType = "5"
  
#发送tcp请求
def send_tcp_request(request):
  data = json.dumps(request.__dict__)
  TCP_CONNECTION.sendall(bytearray(data))

#发送http请求
def send_http_request(request):
  #print json.dumps(request.__dict__)
  post_data = json.dumps(request.__dict__)
  req = urllib2.Request(url = URL, data = post_data)
  req.add_header('Content-Type', 'application/json')
  res_data = urllib2.urlopen(req)
  res = res_data.read()
  if res:
    print res

#过滤并解析数据
def analyze(line):
  if not re.match(MATCH_STR1, line):
    return
  if not re.match(MATCH_STR2, line):
    return
  #获取时间
  sub_line = re.split(' INFO', line)[0]#先分隔出时间字段
  #time_line = re.split('\.', sub_line)[0]#去掉毫秒
  t = time.mktime(time.strptime(sub_line, TIME_FORMAT))#转换成时间戳
  time_head = str(int(t))
  time_foot = re.split('\.', sub_line)[1]
  t = time_head + time_foot
  #类型和topic的名称
  sub_line = re.findall('\[.*?\]', line)#找出有中括号的值
  put_get = sub_line[0]
  put_get = re.sub('[\[\]]', '', put_get)
  topic_group = sub_line[1]
  topic_group = re.sub('[\[\]]', '', topic_group)
  #SUM
  sub_line = re.split('SUM: ', line)
  sum_line = sub_line[1]
  sum = re.split('\s+', sum_line)[0]
  #TPS
  sub_line = re.split('TPS: ', line)
  tps_line = sub_line[1]
  tps = re.split('\s+', tps_line)[0]
  #AVGPT
  sub_line = re.split('AVGPT: ', line)
  avgpt_line = sub_line[1]
  avgpt = re.split('\s+', avgpt_line)[0]
  
  # print 'time:' + t
  # print 'putGet:' + put_get
  # print 'topicGroup:' + topic_group
  # print 'sum:' + sum
  # print 'tps:' + tps
  # print 'avgpt:' + avgpt
  
  request = Request()
  request.ip = LOCAL_IP
  request.time = t
  request.putGet = put_get
  request.topicGroup = topic_group
  request.sum = sum
  request.tps = tps
  request.avgpt = avgpt
  
  send_tcp_request(request)
  

#打开要读取的文件，准备从文件末尾开始读取
def init_read(fileName):
  print LOCAL_IP
  try:
    if os.path.exists(fileName):
      fd = open(fileName, "r")
    else:
      print fileName, "not exist"
      sys.exit(0)
    fd.seek(0, os.SEEK_END)
    label = fd.tell()#开始时文件的结束位置
    while True:
      for line in fd.readlines():
        analyze(line)
      print "next times"
      time.sleep(10)
  finally:
    if os.path.exists(fileName) and fd:
      fd.close()
      
#初始化socket连接
def init_socket():
  global TCP_CONNECTION
  TCP_CONNECTION = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  TCP_CONNECTION.connect((TCP_HOST, TCP_PORT))
  return True

if __name__ == '__main__':
  if len(sys.argv) != 3:
    print "python ", sys.argv[0], "fileName local_ip"
    sys.exit(0)

  fileName = sys.argv[1]
  LOCAL_IP = sys.argv[2]
  
  if not init_socket():
    print 'socket init error'
    sys.exit()
  init_read(fileName)
  
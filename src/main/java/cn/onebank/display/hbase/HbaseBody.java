package cn.onebank.display.hbase;

/**
 * @author shumpert.jiang
 * @date 2017/7/20 0020 18:36
 */
public class HbaseBody {
  private String key;
  private String tps;
  private String logType;
  private String putGet;
  private String topicGroup;
  private String ip;
  private String channelId_;
  private String sum;
  private int port;
  private String time;
  private String avgpt;

  public void setAvgpt(String avgpt) {
    this.avgpt = avgpt;
  }

  public String getAvgpt() {
    return avgpt;
  }

  public String getKey() {
    return key;
  }

  public String getTps() {
    return tps;
  }

  public String getLogType() {
    return logType;
  }

  public String getPutGet() {
    return putGet;
  }

  public String getTopicGroup() {
    return topicGroup;
  }

  public String getIp() {
    return ip;
  }

  public String getChannelId_() {
    return channelId_;
  }

  public String getSum() {
    return sum;
  }

  public int getPort() {
    return port;
  }

  public String getTime() {
    return time;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setTps(String tps) {
    this.tps = tps;
  }

  public void setLogType(String logType) {
    this.logType = logType;
  }

  public void setPutGet(String putGet) {
    this.putGet = putGet;
  }

  public void setTopicGroup(String topicGroup) {
    this.topicGroup = topicGroup;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setChannelId_(String channelId_) {
    this.channelId_ = channelId_;
  }

  public void setSum(String sum) {
    this.sum = sum;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setTime(String time) {
    this.time = time;
  }
}

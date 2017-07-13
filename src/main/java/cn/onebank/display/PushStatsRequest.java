package cn.onebank.display;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 16:02
 */
public class PushStatsRequest {
  String putGet;
  String ip;
  String sum;
  String tps;
  String topicGroup;
  String avgpt;
  String time;

  public String getPutGet() {
    return putGet;
  }

  public String getIp() {
    return ip;
  }

  public String getSum() {
    return sum;
  }

  public String getTps() {
    return tps;
  }

  public String getTopicGroup() {
    return topicGroup;
  }

  public String getAvgpt() {
    return avgpt;
  }

  public String getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "PushStatsRequest{" +
        "putGet='" + putGet + '\'' +
        ", ip='" + ip + '\'' +
        ", sum='" + sum + '\'' +
        ", tps='" + tps + '\'' +
        ", topicGroup='" + topicGroup + '\'' +
        ", avgpt='" + avgpt + '\'' +
        ", time='" + time + '\'' +
        '}';
  }
}

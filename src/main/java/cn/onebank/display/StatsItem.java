package cn.onebank.display;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 14:12
 */
public class StatsItem {
  private String ip;
  private String sum;
  private String tps;
  private String time;
  private String avgpt;

  public StatsItem(String ip, String sum, String tps, String time, String avgpt) {
    this.ip = ip;
    this.sum = sum;
    this.tps = tps;
    this.time = time;
    this.avgpt = avgpt;
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

  public String getTime() {
    return time;
  }

  public String getAvgpt() {
    return avgpt;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public void setSum(String sum) {
    this.sum = sum;
  }

  public void setTps(String tps) {
    this.tps = tps;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public void setAvgpt(String avgpt) {
    this.avgpt = avgpt;
  }

  @Override
  public String toString() {
    return "StatsItem{" +
        "ip='" + ip + '\'' +
        ", sum='" + sum + '\'' +
        ", tps='" + tps + '\'' +
        ", time='" + time + '\'' +
        ", avgpt='" + avgpt + '\'' +
        '}';
  }
}

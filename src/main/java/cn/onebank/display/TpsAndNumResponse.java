package cn.onebank.display;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 10:23
 */
public class TpsAndNumResponse {
  private String tps;
  private String num;

  public TpsAndNumResponse(String tps, String num) {
    this.tps = tps;
    this.num = num;
  }

  public String getTps() {
    return tps;
  }

  public String getNum() {
    return num;
  }

  public void setTps(String tps) {
    this.tps = tps;
  }

  public void setNum(String num) {
    this.num = num;
  }
}

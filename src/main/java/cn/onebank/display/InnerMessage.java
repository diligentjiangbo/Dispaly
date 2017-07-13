package cn.onebank.display;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 15:21
 */
public class InnerMessage {
  private String statsSetKey;
  private String statsKey;
  private StatsItem statsItem;

  public InnerMessage(String statsSetKey, String statsKey, StatsItem statsItem) {
    this.statsSetKey = statsSetKey;
    this.statsKey = statsKey;
    this.statsItem = statsItem;
  }

  public String getStatsSetKey() {
    return statsSetKey;
  }

  public String getStatsKey() {
    return statsKey;
  }

  public StatsItem getStatsItem() {
    return statsItem;
  }

  public void setStatsSetKey(String statsSetKey) {
    this.statsSetKey = statsSetKey;
  }

  public void setStatsKey(String statsKey) {
    this.statsKey = statsKey;
  }

  public void setStatsItem(StatsItem statsItem) {
    this.statsItem = statsItem;
  }

}

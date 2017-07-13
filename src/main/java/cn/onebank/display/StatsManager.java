package cn.onebank.display;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 13:37
 */
public class StatsManager {

  private static final Logger LOGGER = LogManager.getLogger(StatsManager.class);
  private static final StatsManager statsManager = new StatsManager();
  private final HashMap<String, StatsItemSet> statsTable = new HashMap<String, StatsItemSet>();

  public static final String TOPIC_PUT_NUMS = "TOPIC_PUT_NUMS";
  public static final String GROUP_GET_NUMS = "GROUP_GET_NUMS";
  public static final String BROKER_PUT_NUMS = "BROKER_PUT_NUMS";
  public static final String BROKER_GET_NUMS = "BROKER_GET_NUMS";

  private StatsManager() {
    this.statsTable.put(TOPIC_PUT_NUMS, new StatsItemSet(TOPIC_PUT_NUMS));
    this.statsTable.put(GROUP_GET_NUMS, new StatsItemSet(GROUP_GET_NUMS));
    this.statsTable.put(BROKER_PUT_NUMS, new StatsItemSet(BROKER_PUT_NUMS));
    this.statsTable.put(BROKER_GET_NUMS, new StatsItemSet(BROKER_GET_NUMS));
  }

  public static StatsManager getInstance() {
    return statsManager;
  }

  public void modify(String statsSetKey, String statsKey, StatsItem statsItem) {
    this.statsTable.get(statsSetKey).modifyValue(statsKey, statsItem);
  }

  public TpsAndNumResponse getInTpsAndNum() {
    Set<Map.Entry<String, StatsItem>> set  = this.statsTable.get(BROKER_PUT_NUMS).getStatsItemTable().entrySet();
    Iterator<Map.Entry<String, StatsItem>> it = set.iterator();
    if (it.hasNext()) {
      StatsItem statsItem = it.next().getValue();
      String tps = statsItem.getTps();
      String num = statsItem.getSum();
      return new TpsAndNumResponse(tps, num);
    }
    return null;
  }

  public Long getOutTpsAndNum() {

    return 0L;
  }

  public void getTop5In() {

  }

  public void getTop5Out() {

  }



}

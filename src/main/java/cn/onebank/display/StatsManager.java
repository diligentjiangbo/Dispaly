package cn.onebank.display;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

  public List<Top5Response> getTop5() {
    ConcurrentHashMap<String, ConcurrentHashMap<String, StatsItem>> groupTopicMap =
        this.statsTable.get(GROUP_GET_NUMS).getGroupTable();
    List<Top5Response> retList = new ArrayList<Top5Response>();
    //遍历统计
    Set<Map.Entry<String, ConcurrentHashMap<String, StatsItem>>> entrySet = groupTopicMap.entrySet();
    Iterator<Map.Entry<String, ConcurrentHashMap<String, StatsItem>>> it = entrySet.iterator();
    while (it.hasNext()) {
      Map.Entry<String, ConcurrentHashMap<String, StatsItem>> entry = it.next();
      String groupName = entry.getKey();
      LOGGER.info("getTop5, groupName={}", groupName);
      double tps = 0;
      ConcurrentHashMap<String, StatsItem> topicMap = entry.getValue();
      for (Map.Entry<String, StatsItem> topicEntry : topicMap.entrySet()) {
        double oneTps = Double.parseDouble(topicEntry.getValue().getTps());
        tps += oneTps;
      }
      Top5Response top5Response = new Top5Response(groupName, String.valueOf(tps));
      retList.add(top5Response);
    }
    //排序
    Collections.sort(retList);
    return retList;
  }



}

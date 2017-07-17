package cn.onebank.display;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 13:48
 */
public class StatsItemSet {

  private static final Logger LOGGER = LogManager.getLogger(StatsItemSet.class);
  /**
   * key:H80-3103030007-01这是TOPIC_PUT_NUMS
   * key:ZP、WGQ和DefaultCluster这是BROKER_GET_NUMS和BROKER_PUT_NUMS
   */
  private final ConcurrentHashMap<String, StatsItem> statsItemTable = new ConcurrentHashMap<String, StatsItem>();
  private final ConcurrentHashMap<String/*group*/, ConcurrentHashMap<String/*topic*/, StatsItem>> groupTable = new ConcurrentHashMap<String, ConcurrentHashMap<String, StatsItem>>();
  private final String statsName;

  public StatsItemSet(String statsName) {
    this.statsName = statsName;
  }

  public void modifyValue(String statsKey, StatsItem statsItem) {
    if (statsName != null && StatsManager.GROUP_GET_NUMS.equals(statsName)) {
      String[] groupTopic = statsKey.split("@");
      if (groupTopic.length != 2) {
        LOGGER.warn("{}:{}格式不正确", StatsManager.GROUP_GET_NUMS, statsKey);
      } else {
        String group = groupTopic[1];
        String topic = groupTopic[0];
        ConcurrentHashMap<String, StatsItem> topicMap = groupTable.get(group);
        if (topicMap == null) {
          topicMap = new ConcurrentHashMap<String, StatsItem>();
          groupTable.put(group, topicMap);
        }
        topicMap.put(topic, statsItem);
        LOGGER.debug("modify [statsName={}, statsKey={}, statsItem={}]", statsName, statsKey, statsItem);
      }
    } else {
      StatsItem oldStatsItem = statsItemTable.put(statsKey, statsItem);
      LOGGER.debug("modify [statsName={}, statsKey={}, statsItem={}]", statsName, statsKey, statsItem);
    }
  }

  public ConcurrentHashMap<String, StatsItem> getStatsItemTable() {
    return this.statsItemTable;
  }

  public ConcurrentHashMap<String, ConcurrentHashMap<String, StatsItem>> getGroupTable() {
    return groupTable;
  }
}

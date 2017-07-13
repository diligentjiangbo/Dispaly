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
  private final ConcurrentHashMap<String, StatsItem> statsItemTable = new ConcurrentHashMap<String, StatsItem>(128);

  private final String statsName;

  public StatsItemSet(String statsName) {
    this.statsName = statsName;
  }

  public void modifyValue(String statsKey, StatsItem statsItem) {
    StatsItem oldStatsItem = statsItemTable.put(statsKey, statsItem);
    LOGGER.debug("modify [statsName={}, statsKey={}, statsItem={}]", statsName, statsKey, oldStatsItem);
  }

  public ConcurrentHashMap<String, StatsItem> getStatsItemTable() {
    return this.statsItemTable;
  }

}

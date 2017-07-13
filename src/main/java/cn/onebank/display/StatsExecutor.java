package cn.onebank.display;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 15:10
 */
public class StatsExecutor implements Runnable {

  private static final Logger LOGGER = LogManager.getLogger(StatsExecutor.class);


  public void run() {
    while (true) {
      try {
        InnerMessage innerMessage = MemoryQueue.getInstance().getMessage();
        String statsSetKey = innerMessage.getStatsSetKey();
        String statsKey = innerMessage.getStatsKey();
        StatsItem statsItem = innerMessage.getStatsItem();
        StatsManager.getInstance().modify(statsSetKey, statsKey, statsItem);
      } catch (InterruptedException e) {
        LOGGER.warn("等待内存队列消息时被中断");
        continue;
      }
    }
  }

}

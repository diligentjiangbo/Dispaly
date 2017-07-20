package cn.onebank.display;

import cn.onebank.display.hbase.HBaseStatsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 15:56
 * 主动从hBase拉取数据
 */
public class PullStatsController implements Runnable {

  private static final Logger LOGGER = LogManager.getLogger(PullStatsController.class);

  @Override
  public void run() {
    List<InnerMessage> list = null;
    try {
      list = HBaseStatsManager.getInstance().PullStats();
    } catch (IOException e) {
      LOGGER.error("从hbase拉取数据异常", e);
      return;
    }
    for (InnerMessage innerMessage : list) {
      try {
        LOGGER.info(innerMessage.getStatsItem());
        MemoryQueue.getInstance().putMessage(innerMessage);
      } catch (InterruptedException e) {
        LOGGER.error("放入统计消息被中断");
      }
    }
  }

}

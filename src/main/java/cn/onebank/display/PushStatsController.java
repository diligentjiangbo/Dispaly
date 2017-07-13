package cn.onebank.display;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 15:59
 */
@RestController
public class PushStatsController {

  private static final Logger LOGGER = LogManager.getLogger(PushStatsController.class);

  @RequestMapping(value = "/pushStats", method = RequestMethod.POST)
  public void PushStats(@RequestBody PushStatsRequest pushStatsRequest) {
    LOGGER.info("recv stats:{}", pushStatsRequest);
    StatsItem statsItem = new StatsItem(pushStatsRequest.getIp(), pushStatsRequest.getSum(), pushStatsRequest.getTps(), pushStatsRequest.getTime(), pushStatsRequest.getAvgpt());
    InnerMessage innerMessage = new InnerMessage(pushStatsRequest.getPutGet(), pushStatsRequest.getTopicGroup(), statsItem);
    try {
      MemoryQueue.getInstance().putMessage(innerMessage);
    } catch (InterruptedException e) {
      LOGGER.error("放入统计消息被中断");
    }
  }

}

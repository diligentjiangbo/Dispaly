package cn.onebank.display;

import cn.onebank.display.hbase.HBaseStatsManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 10:26
 */
@SpringBootApplication
public class App {
  private static final Logger LOGGER = LogManager.getLogger(App.class);
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    try {
      Config.loadConfig();
      Config.printConfig();
    } catch (IOException e) {
      LOGGER.error("加载配置文件错误", e);
      return;
    }

    //初始化Hbase offset
    try {
      HBaseStatsManager.getInstance().init();
    } catch (IOException e) {
      LOGGER.error("初始化hbase offset失败", e);
      return;
    }

    //消费者线程池
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new StatsExecutor());
    //生产者线程池
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(new PullStatsController(), 0, 30000, TimeUnit.MILLISECONDS);

  }
}

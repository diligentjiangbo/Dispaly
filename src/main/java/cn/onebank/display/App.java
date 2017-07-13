package cn.onebank.display;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 10:26
 */
@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.execute(new StatsExecutor());
  }
}

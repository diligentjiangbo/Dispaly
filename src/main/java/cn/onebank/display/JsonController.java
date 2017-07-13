package cn.onebank.display;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 10:17
 */
@RestController
public class JsonController {

  private static final Logger LOGGER = LogManager.getLogger(JsonController.class);
  private static final Random RANDOM = new Random();
  private static final Gson GSON = new Gson();

  @RequestMapping(value = "getTpsAndNum", method = RequestMethod.GET)
  public String getTpsAndNum() {
    LOGGER.info("recv request getTpsAndNum");
    String tps = String.valueOf(RANDOM.nextInt(10));
    String num = String.valueOf(RANDOM.nextInt(100));
    TpsAndNumResponse tpsAndNumResponse = new TpsAndNumResponse(tps, num);
    return GSON.toJson(tpsAndNumResponse);
  }
}

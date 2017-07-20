package cn.onebank.display;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    LOGGER.debug("recv request getTpsAndNum");
//    String tps = String.valueOf(RANDOM.nextInt(10));
//    String num = String.valueOf(RANDOM.nextInt(100));
//    TpsAndNumResponse tpsAndNumResponse = new TpsAndNumResponse(tps, num);
    TpsAndNumResponse tpsAndNumResponse = StatsManager.getInstance().getInTpsAndNum();
    return GSON.toJson(tpsAndNumResponse);
  }

  /**
   * 返回top5
   */
  @RequestMapping(value = "getTop5", method = RequestMethod.GET)
  public String getTop5() {
    LOGGER.debug("recv request getTop5");
//    List<Top5Response> list = new ArrayList<Top5Response>();
//    for (int i = 0; i < 5; i++) {
//      list.add(new Top5Response("top5" + i, String.valueOf(RANDOM.nextInt(50))));
//    }
//    Collections.sort(list);
    List<Top5Response> list = StatsManager.getInstance().getTop5();
    int length = 5;
    if (list.size() < 5) {
      length = list.size();
    }
    List<Top5Response> retList = list.subList(0, length);

    return GSON.toJson(retList);
  }

}

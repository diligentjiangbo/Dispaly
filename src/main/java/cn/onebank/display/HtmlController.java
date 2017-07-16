package cn.onebank.display;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 10:16
 */
@Controller
public class HtmlController {
  @RequestMapping("/display")
  public String display() {
    return "display";
  }

  @RequestMapping("top5")
  public String top5() {
    return "top5";
  }
}

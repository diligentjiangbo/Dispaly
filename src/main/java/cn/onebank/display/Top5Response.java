package cn.onebank.display;

import com.google.gson.Gson;

import java.util.*;

/**
 * @author shumpert.jiang
 * @date 2017/7/16 0016 17:11
 */
public class Top5Response implements Comparable<Top5Response> {
  String name;
  String tps;

  public Top5Response(String name, String tps) {
    this.name = name;
    this.tps = tps;
  }

  public String getName() {
    return name;
  }

  public String getTps() {
    return tps;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTps(String tps) {
    this.tps = tps;
  }

  @Override
  public String toString() {
    return "Top5Response{" +
        "name='" + name + '\'' +
        ", tps='" + tps + '\'' +
        '}';
  }

  @Override
  public int compareTo(Top5Response o) {
    if (Long.parseLong(this.tps) >= Long.parseLong(o.tps)) {
      return -1;
    } else {
      return 1;
    }
  }

  public static void main(String[] args) {
    Random random = new Random();
    List<Top5Response> list = new ArrayList<Top5Response>();
    for (int i = 0; i < 5; i++) {
      list.add(new Top5Response(i+"top5", String.valueOf(random.nextInt(100))));
    }

    Iterator<Top5Response> it = list.iterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }

    Collections.sort(list);
    System.out.println("sort-==-==-==-==-==");

    Iterator<Top5Response> it2 = list.iterator();
    while (it2.hasNext()) {
      System.out.println(it2.next());
    }

    Gson gson = new Gson();
    System.out.println(gson.toJson(list));

  }
}

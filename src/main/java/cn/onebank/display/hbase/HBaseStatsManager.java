package cn.onebank.display.hbase;

import cn.onebank.display.Config;
import cn.onebank.display.InnerMessage;
import cn.onebank.display.StatsItem;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author shumpert.jiang
 * @date 2017/7/19 0019 16:37
 * 
 */
public class HBaseStatsManager {

  //单例
  private static final HBaseStatsManager hBaseStatsManager = new HBaseStatsManager();
  
  //hbase连接
  private static Connection conn;

  //init方法只调用一次的标志
  private boolean first = true;
  private String stopRow = null;

  private HBaseStatsManager() {}

  /**
   * 第一次去定位最后一个row
   * @throws IOException
   */
  public void init() throws IOException {
    if (first) {
      first = false;
      Table table = getConn().getTable(TableName.valueOf(Config.getTableName()));
      Scan scan = new Scan();
      //倒序查，拿最后一个row
      scan.setReversed(true);
      ResultScanner rs = table.getScanner(scan);
      Iterator<Result> it = rs.iterator();
      if (it.hasNext()) {
        Result result = it.next();
        CellScanner cellScanner = result.cellScanner();
        if (cellScanner.advance()) {
          Cell current = cellScanner.current();
          byte[] rowArray = current.getRowArray();
          stopRow = new String(rowArray, current.getRowOffset(), current.getRowLength());
        }
      }
    }
  }

  public static HBaseStatsManager getInstance() {
    return hBaseStatsManager;
  }

  public List<InnerMessage> PullStats() throws IOException {
    List<InnerMessage> retList = new ArrayList<InnerMessage>();
    Table table = getConn().getTable(TableName.valueOf(Config.getTableName()));
    Scan scan = new Scan(stopRow.getBytes());
    ResultScanner rs = table.getScanner(scan);
    JSONArray array = new JSONArray();
    for (Result r : rs) {
      CellScanner cellScanner = r.cellScanner();
      JSONObject json = new JSONObject();
      while (cellScanner.advance()) {
        Cell current = cellScanner.current();
        byte[] valueArray = current.getValueArray();
        byte[] qualifierArray = current.getQualifierArray();
        byte[] rowArray = current.getRowArray();
        stopRow = new String(rowArray, current.getRowOffset(), current.getRowLength());
        String qualifier = new String(qualifierArray, current.getQualifierOffset(), current.getQualifierLength());
        String value = new String(valueArray, current.getValueOffset(), current.getValueLength());
        json.put(qualifier, value);
      }
      array.add(json);
    }
    for (int i = 0; i < array.size(); i++) {
      JSONObject object = array.getJSONObject(i);
      HbaseBody hbaseBody = object.parseObject(object.toJSONString(), HbaseBody.class);
      String ip = hbaseBody.getIp();
      String sum = hbaseBody.getSum();
      String tps = hbaseBody.getTps();
      String time = hbaseBody.getTime();
      String avgpt = hbaseBody.getAvgpt();
      StatsItem statsItem = new StatsItem(ip, sum, tps, time, avgpt);
      String statsSetKey = hbaseBody.getPutGet();
      String statsKey = hbaseBody.getTopicGroup();
      InnerMessage innerMessage = new InnerMessage(statsSetKey, statsKey, statsItem);
      retList.add(innerMessage);
    }
    return retList;
  }

  /**
   * 获取一个hbase的连接
   * @return
   */
  private static Connection getConn() throws IOException {
    if (conn == null || conn.isClosed()) {
      Configuration configuration = HBaseConfiguration.create();
      configuration.set(Config.ZK_QUORUM, Config.getZkQuorum());
      configuration.set(Config.ZK_PORT, Config.getZkPort());
      configuration.set(Config.ZK_NODE_PARENT, Config.getZkNodeParent());
      conn = ConnectionFactory.createConnection(configuration);
    }
    return conn;
  }

}

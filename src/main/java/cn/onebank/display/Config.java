package cn.onebank.display;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @author shumpert.jiang
 * @date 2017/7/20 0020 9:37
 */
public class Config {
  private static final Logger LOGGER = LogManager.getLogger(Config.class);

  public static String configRootPath = null;
  public static final String ROOT_PATH_NAME = "DisplayPath";
  public static final String FILENAME = "display.properties";

  public static final String ZK_QUORUM = "hbase.zookeeper.quorum";
  public static final String ZK_PORT = "hbase.zookeeper.property.clientPort";
  public static final String ZK_NODE_PARENT = "zookeeper.znode.parent";
  public static final String TABLE_NAME = "hbase.table.name";

  private static String zkQuorum;
  private static String zkPort;
  private static String zkNodeParent;
  private static String tableName;

  static {
    if (System.getProperty(ROOT_PATH_NAME) != null) {
      configRootPath = System.getProperty(ROOT_PATH_NAME);
    } else {
      configRootPath = Config.class.getResource("/").getPath();
    }
  }

  public static void loadConfig() throws IOException {
    String path = configRootPath + File.separator + FILENAME;
    Properties prop = new Properties();
    InputStream input = null;

    try {
      input = new FileInputStream(path);
      prop.load(input);

      zkQuorum = prop.getProperty(ZK_QUORUM);
      zkPort = prop.getProperty(ZK_PORT);
      zkNodeParent = prop.getProperty(ZK_NODE_PARENT);
      tableName = prop.getProperty(TABLE_NAME);
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          LOGGER.error("关闭读取配置文件流失败", e);
        }
      }
    }
  }

  public static void printConfig() {
    LOGGER.info("---------configuration-----------");

    LOGGER.info("zkQuorum: {}", zkQuorum);
    LOGGER.info("zkPort: {}", zkPort);
    LOGGER.info("zkNodeParent: {}", zkNodeParent);
    LOGGER.info("tableName: {}", tableName);

    LOGGER.info("-----------------------------------");
  }

  public static String getZkQuorum() {
    return zkQuorum;
  }

  public static String getZkPort() {
    return zkPort;
  }

  public static String getZkNodeParent() {
    return zkNodeParent;
  }

  public static String getTableName() {
    return tableName;
  }

}

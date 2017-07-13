package cn.onebank.display;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author shumpert.jiang
 * @date 2017/7/13 0013 15:18
 * 获取统计信息与更新统计信息解耦的内存队列
 */
public class MemoryQueue {

  private static final MemoryQueue memoryQueue = new MemoryQueue();
  private final BlockingQueue<InnerMessage> blockingQueue = new ArrayBlockingQueue<InnerMessage>(128);

  private MemoryQueue() {}

  public static MemoryQueue getInstance() {
    return memoryQueue;
  }

  public void putMessage(InnerMessage innerMessage) throws InterruptedException {
    blockingQueue.put(innerMessage);
  }

  public InnerMessage getMessage() throws InterruptedException {
    return blockingQueue.take();
  }

}

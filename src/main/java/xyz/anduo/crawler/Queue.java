package xyz.anduo.crawler;

import java.util.LinkedList;

/**
 * 队列,保存将要访问的URL
 * 
 * @author anduo
 * 
 */
public class Queue {

  // 使用链表实现队列
  @SuppressWarnings("rawtypes")
  private LinkedList queue = new LinkedList();

  // 入队列
  @SuppressWarnings("unchecked")
  public void enQueue(Object o) {
    queue.add(o);
  }

  // 出队列
  public Object deQueue() {
    return queue.removeFirst();

  }

  // 判断队列是否为空
  public boolean isQueueEmpty() {
    return queue.isEmpty();
  }

  // 判断队列是否包含o
  public boolean contians(Object o) {
    return queue.contains(o);
  }

}

package xyz.anduo.crawler;

import java.util.HashSet;
import java.util.Set;

public class LinkQueue {
  // 已访问的url集合
  @SuppressWarnings("rawtypes")
  private static Set visitedUrl = new HashSet();

  // 待访问的url集合
  private static Queue unVisitedUrl = new Queue();

  // private static Queue unVisitedUrl = new PriorityQueue();//带偏好的爬虫，使用支持优先队列的数据结构

  // 获取URL队列
  public static Queue getUnVisitedUrl() {
    return unVisitedUrl;
  }

  // 添加到访问过的url队列中
  @SuppressWarnings("unchecked")
  public static void addVisitedUrl(String url) {
    visitedUrl.add(url);
  }

  // 移除访问过的url
  public static void removeVisitedUrl(String url) {
    visitedUrl.remove(url);
  }

  // 未访问的url出队列
  public static Object unVisitedUrlDeQueue() {
    return unVisitedUrl.deQueue();
  }

  // 保证每个url只被访问一次
  public static void addUnVisitedUrl(String url) {
    if (url != null && !"".equals(url.trim()) && !visitedUrl.contains(url)
        && !unVisitedUrl.contians(url)) {
      unVisitedUrl.enQueue(url);
    }
  }

  // 获得已经访问的url数目
  public static int getVisitedUrlNum() {
    return visitedUrl.size();
  }

  // 判断未访问的url队列中是否为空
  public static boolean unVisitedUrlIsEmpty() {
    return unVisitedUrl.isQueueEmpty();
  }

}

package xyz.anduo.crawler.hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致hash算法的实现
 * 
 * @author anduo
 * 
 * @param <T>
 */
public class ConsistentHash<T> {

  private final HashFunction hashFunction; // hash 函数接口
  private final int numberOfReplicas;// 每个机器节点关联的虚拟节点个数
  private final SortedMap<Long, T> circle = new TreeMap<Long, T>();// 环形虚拟节点

  /**
   * @param hashFunction hash 函数接口
   * @param numberOfReplicas 每个机器节点关联的虚拟节点个数
   * @param nodes 真实机器节点
   */
  public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes// 物理节点
  ) {
    this.hashFunction = hashFunction;
    this.numberOfReplicas = numberOfReplicas;
    for (T node : nodes) {
      add(node);
    }
  }

  /**
   * 增加真实机器节点
   * 
   * @param node
   */
  public void add(T node) {
    for (int i = 0; i < numberOfReplicas; i++) {
      circle.put(hashFunction.hash(node.toString() + i), node);
    }
  }

  /**
   * 删除真实机器节点
   * 
   * @param node
   */
  public void remove(T node) {
    for (int i = 0; i < numberOfReplicas; i++) {
      circle.remove(hashFunction.hash(node.toString() + i));
    }
  }

  /**
   * 取得真实机器节点
   * 
   * @param key
   * @return
   */
  public T get(String key) {// 关键算法
    if (circle.isEmpty()) {
      return null;
    }
    // 计算hash值
    Long hash = hashFunction.hash(key);
    // 如果不包含这个hash值
    if (!circle.containsKey(hash)) {
      SortedMap<Long, T> tailMap = circle.tailMap(hash);// 沿环的顺时针找到一个虚拟节点
      hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
    }
    return circle.get(hash);// 返回该虚拟节点对应的真实机器节点的信息
  }



}

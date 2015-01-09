package xyz.anduo.crawler.hash;

/**
 * hash 函数接口
 * 
 * @author anduo
 * 
 */
public interface HashFunction {
  /**
   * hash函数
   * 
   * @param key
   * @return
   */
  Long hash(String key);
}

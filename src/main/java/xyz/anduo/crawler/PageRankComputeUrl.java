package xyz.anduo.crawler;

/**
 * 使用pagerank算法实现的url计算方法
 * 
 * @author anduo
 * 
 */
public class PageRankComputeUrl implements ComputeUrl {

  /**
   * 针对url进行连接分析
   */
  public boolean accept(String url, String pageContent) {
    return false;
  }

}

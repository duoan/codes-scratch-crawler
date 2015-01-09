package xyz.anduo.crawler;

/**
 * 主题爬虫，计算接口
 * @author anduo
 *
 */
public interface ComputeUrl {
  public boolean accept(String url, String pageContent);
}

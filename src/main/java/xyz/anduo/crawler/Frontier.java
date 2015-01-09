package xyz.anduo.crawler;

public interface Frontier {
  public CrawlUrl getNext() throws Exception;

  public boolean putUrl(CrawlUrl url) throws Exception;
}

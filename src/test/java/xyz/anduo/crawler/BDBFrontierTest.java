package xyz.anduo.crawler;

import junit.framework.TestCase;

public class BDBFrontierTest extends TestCase {
  public void test() {
    try {
      BDBFrontier bdbFrontier = new BDBFrontier("d:\\dbd");
      CrawlUrl url = new CrawlUrl();
      url.setOriUrl("http://www.163.com");
      bdbFrontier.putUrl(url);
      assertEquals(bdbFrontier.getNext().getOriUrl(), "http://www.163.com");
      bdbFrontier.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

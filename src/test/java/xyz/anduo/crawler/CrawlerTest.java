package xyz.anduo.crawler;

import junit.framework.TestCase;

public class CrawlerTest extends TestCase {

  public void testCrawling() {
    Crawler crawler = new Crawler();
    crawler.crawling(new String[] {"http://blog.sina.com.cn/"});
  }

}

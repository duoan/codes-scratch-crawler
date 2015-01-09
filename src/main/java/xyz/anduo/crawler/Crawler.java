package xyz.anduo.crawler;

import java.util.Set;

public class Crawler {

  //用于主题爬虫
  private ComputeUrl computeUrl = null;

  public Crawler() {
    computeUrl = new PageRankComputeUrl();
  }

  /**
   * 使用种子初始化抓取队列
   * 
   * @param seeds
   */
  private void initCrawlerWithSeeds(String[] seeds) {
    for (int i = 0; i < seeds.length; i++) {
      LinkQueue.addUnVisitedUrl(seeds[i]);
    }
  }

  public void crawling(String[] seeds) {
    // 初始化url队列
    initCrawlerWithSeeds(seeds);
    // 循环条件：待抓取的连接不空且抓取的网页不多于1000
    while (!LinkQueue.unVisitedUrlIsEmpty() && LinkQueue.getVisitedUrlNum() <= 1000) {

      LinkFilter filter = new LinkFilter() {

        public boolean accept(String url) {
          if (url.startsWith("http://blog.sina.com.cn")) {
            return true;
          } else {
            return false;
          }
        }
      };

      // 对头url出队
      String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
      if (visitUrl == null) {
        continue;
      }

      DownLoadFile downLoader = new DownLoadFile();
      // 下载网页
      String content = downLoader.downloadFile(visitUrl);
      if (computeUrl.accept(visitUrl, content)) {
        continue;
      }
      // 该url放入已访问的url中
      LinkQueue.addVisitedUrl(visitUrl);
      // 提取出下载网页中的url
      Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
      // 新的未访问的url入队
      for (String url : links) {
        LinkQueue.addUnVisitedUrl(url);
      }
    }

  }


}

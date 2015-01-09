package xyz.anduo.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParserTool {

  public static Set<String> extracLinks(String url, LinkFilter filter) {
    Set<String> result = new HashSet<String>();
    Document doc;
    try {
      doc = Jsoup.connect(url).timeout(5000).get();
      Elements links = doc.select("a[href]");
      Elements frames = doc.select("frame[src]");
      Elements iframes = doc.select("iframe[src]");
      for (Element e : links) {
        System.out.println(e.absUrl("href"));
        if (filter.accept(e.absUrl("href")))
          result.add(e.absUrl("href"));
      }
      for (Element e : frames) {
        if (filter.accept(e.absUrl("src")))
          result.add(e.absUrl("src"));
      }
      for (Element e : iframes) {
        if (filter.accept(e.absUrl("src")))
          result.add(e.absUrl("src"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}

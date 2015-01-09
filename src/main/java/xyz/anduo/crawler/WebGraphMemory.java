package xyz.anduo.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * WebGraphMemory 内存Web图
 * 
 * @author anduo
 * 
 */
public class WebGraphMemory {
  // 把每个URL映射为一个整数，存储在web图中
  private Map<Integer, String> IdentifyerToURL;

  /**
   * A Map storing relationships from URLs to numeric identifiers, usefull for storing Web graphs
   */
  private Map<String, Map<String, Integer>> URLToIdentifyer;

  /**
   * 存储入度，其中整数第一个参数是URL的ID，第二个参数是存放指向这个URL链接的Map，Double表示权重
   */
  private Map<Integer, Map<Integer, Double>> InLinks;

  /**
   * 存储出度，其中第一个参数是URL的ID，第二个参数是存放网页中的超链接，Double表示权重
   */
  private Map<Integer, Map<Integer, Double>> OutLinks;
  /** 图中节点的数目 */
  private int nodeCount;

  /**
   * 构造函数，0个节点的构造函数
   */
  public WebGraphMemory() {
    IdentifyerToURL = new HashMap<Integer, String>();
    URLToIdentifyer = new HashMap<String, Map<String, Integer>>();
    InLinks = new HashMap<Integer, Map<Integer, Double>>();
    OutLinks = new HashMap<Integer, Map<Integer, Double>>();
    nodeCount = 0;
  }

  /**
   * 从一个文本文件中取得节点的构造函数。 每行包含一个指向关系。例如： http://url1.com -> http://url2.com 1.0 表示 "http://url1.com"
   * 包含一个超链接 "http://url2.com", 并且这个超链接的权重是1.0
   */
  @SuppressWarnings("resource")
  public WebGraphMemory(File file) throws IOException, FileNotFoundException {
    this();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = reader.readLine()) != null) {
      int index1 = line.indexOf("->");
      if (index1 == -1)
        addLink(line.trim());
      else {
        String url1 = line.substring(0, index1).trim();
        String url2 = line.substring(index1 + 2).trim();
        Double strength = new Double(1.0);
        index1 = url2.indexOf(" ");
        if (index1 != -1)
          try {
            strength = new Double(url2.substring(index1 + 1).trim());
            url2 = url2.substring(0, index1).trim();
          } catch (Exception e) {
          }
        addLink(url1, url2, strength);
      }
    }
  }

  /**
   * 根据URL制定它的ID
   */
  public Integer URLToIdentifyer(String URL) {
    String host;
    String name;
    int index = 0, index2 = 0;
    if (URL.startsWith("http://"))
      index = 7;
    else if (URL.startsWith("ftp://"))
      index = 6;
    index2 = URL.substring(index).indexOf("/");
    if (index2 != -1) {
      name = URL.substring(index + index2 + 1);
      host = URL.substring(0, index + index2);
    } else {
      host = URL;
      name = "";
    }
    // System.out.println("host:"+host + " name:"+name);
    Map<String, Integer> map = (URLToIdentifyer.get(host));
    if (map == null) {
      // System.out.println("will return null");
      return null;
    }
    // System.out.println("return:"+map.get(""));
    return (map.get(name));
  }

  /**
   * 根据ID获得URL
   */
  public String IdentifyerToURL(Integer id) {
    return (IdentifyerToURL.get(id));
  }

  /**
   * 在图中增加一个节点
   */
  public Integer addLink(String link) {
    Integer id = URLToIdentifyer(link);
    if (id == null) {
      id = new Integer(++nodeCount);
      String host;
      String name;
      int index = 0, index2 = 0;
      if (link.startsWith("http://"))
        index = 7;
      else if (link.startsWith("ftp://"))
        index = 6;
      index2 = link.substring(index).indexOf("/");
      if (index2 != -1) {
        name = link.substring(index + index2 + 1);
        host = link.substring(0, index + index2);
      } else {
        host = link;
        name = "";
      }
      // System.out.println("HOST:"+host + " name:"+name);
      Map<String, Integer> map = (URLToIdentifyer.get(host));
      if (map == null) {
        map = new HashMap<String, Integer>();
        URLToIdentifyer.put(host, map);
      }
      map.put(name, id);

      // error here
      // URLToIdentifyer.put(link,map);
      IdentifyerToURL.put(id, link);
      InLinks.put(id, new HashMap<Integer, Double>());
      OutLinks.put(id, new HashMap<Integer, Double>());
      // System.out.println("id:"+id);
    }
    return id;
  }

  /**
   * 在两个节点中增加一个对应关系。如果节点不存在，就新创建节点
   */
  public Double addLink(String fromLink, String toLink, Double weight) {
    Integer id1 = addLink(fromLink);
    Integer id2 = addLink(toLink);
    return addLink(id1, id2, weight);
  }

  /**
   * 在两个节点中增加一个对应关系。如果节点不存在，就新创建节点
   */
  private Double addLink(Integer fromLink, Integer toLink, Double weight) {
    // System.out.println("from "+fromLink+" to "+toLink);
    Double aux;
    Map<Integer, Double> map1 = (InLinks.get(toLink));
    Map<Integer, Double> map2 = (OutLinks.get(fromLink));
    aux = (Double) (map1.get(fromLink));
    if (aux == null)
      map1.put(fromLink, weight);
    else if (aux.doubleValue() < weight.doubleValue())
      map1.put(fromLink, weight);
    else
      weight = new Double(aux.doubleValue());

    aux = (map2.get(toLink));
    if (aux == null)
      map2.put(toLink, weight);
    else if (aux.doubleValue() < weight.doubleValue())
      map2.put(toLink, weight);
    else {
      weight = new Double(aux.doubleValue());
      map1.put(fromLink, weight);
    }
    InLinks.put(toLink, map1);
    OutLinks.put(fromLink, map2);
    return weight;
  }

  /**
   * 针对指定的URL返回包含它的入度的链接的Map
   */
  public Map<Integer, Double> inLinks(String URL) {
    Integer id = URLToIdentifyer(URL);
    return inLinks(id);
  }

  /**
   * 针对指定的URL返回包含它的入度的链接的Map
   */
  public Map<Integer, Double> inLinks(Integer link) {
    if (link == null)
      return null;
    Map<Integer, Double> aux = (InLinks.get(link));
    return aux;
  }

  /**
   * 针对指定的URL返回包含它的出度的链接的Map
   */
  public Map<Integer, Double> outLinks(String URL) {
    Integer id = URLToIdentifyer(URL);
    return outLinks(id);
  }

  /**
   * 针对指定的URL返回包含它的出度的链接的Map
   */
  public Map<Integer, Double> outLinks(Integer link) {
    if (link == null)
      return null;
    Map<Integer, Double> aux = OutLinks.get(link);
    return aux;
  }

  /**
   * 返回两个节点之间的权重，如果节点没有连接，就返回0
   */
  public Double inLink(String fromLink, String toLink) {
    Integer id1 = URLToIdentifyer(fromLink);
    Integer id2 = URLToIdentifyer(toLink);
    return inLink(id1, id2);
  }

  /**
   * 返回两个节点之间的权重，如果节点没有连接，就返回0
   */
  public Double outLink(String fromLink, String toLink) {
    Integer id1 = URLToIdentifyer(fromLink);
    Integer id2 = URLToIdentifyer(toLink);
    return outLink(id1, id2);
  }

  /**
   * 返回两个节点之间的权重，如果节点没有连接，就返回0
   */
  public Double inLink(Integer fromLink, Integer toLink) {
    Map<Integer, Double> aux = inLinks(toLink);
    if (aux == null)
      return new Double(0);
    Double weight = (aux.get(fromLink));
    return (weight == null) ? new Double(0) : weight;
  }

  /**
   * 返回两个节点之间的权重，如果节点没有连接，就返回0
   */
  public Double outLink(Integer fromLink, Integer toLink) {
    Map<Integer, Double> aux = outLinks(fromLink);
    if (aux == null)
      return new Double(0);
    Double weight = (aux.get(toLink));
    return (weight == null) ? new Double(0) : weight;
  }

  /**
   * 把有向图变为无向图。
   */
  public void transformUnidirectional() {
    Iterator<Integer> it = OutLinks.keySet().iterator();
    while (it.hasNext()) {
      Integer link1 = (Integer) (it.next());
      Map<Integer, Double> auxMap = OutLinks.get(link1);
      Iterator<Integer> it2 = auxMap.keySet().iterator();
      while (it2.hasNext()) {
        Integer link2 = (Integer) (it.next());
        Double weight = (Double) (auxMap.get(link2));
        addLink(link2, link1, weight);
      }
    }
  }

  /**
   * 删除内部链接，内部链接就是指在同一主机上的链接
   */
  public void removeInternalLinks() {
    int index1;
    Iterator<Integer> it = OutLinks.keySet().iterator();
    while (it.hasNext()) {
      Integer link1 = (Integer) (it.next());
      Map<Integer, Double> auxMap = (OutLinks.get(link1));
      Iterator<Integer> it2 = auxMap.keySet().iterator();
      if (it2.hasNext()) {
        String URL1 = (String) (IdentifyerToURL.get(link1));
        index1 = URL1.indexOf("://");
        if (index1 != -1)
          URL1 = URL1.substring(index1 + 3);
        index1 = URL1.indexOf("/");
        if (index1 != -1)
          URL1 = URL1.substring(0, index1);
        while (it2.hasNext()) {
          Integer link2 = (Integer) (it.next());
          String URL2 = (String) (IdentifyerToURL.get(link2));
          index1 = URL2.indexOf("://");
          if (index1 != -1)
            URL2 = URL1.substring(index1 + 3);
          index1 = URL2.indexOf("/");
          if (index1 != -1)
            URL2 = URL1.substring(0, index1);
          if (URL1.equals(URL2)) {
            auxMap.remove(link2);
            OutLinks.put(link1, auxMap);
            auxMap = (InLinks.get(link2));
            auxMap.remove(link1);
            InLinks.put(link2, auxMap);
          }
        }
      }
    }
  }

  /**
   * 删除内部导航链接。
   */
  public void removeNepotistic() {
    removeInternalLinks();
  }

  /**
   * 删除 stop URLs.。
   */
  public void removeStopLinks(String stopURLs[]) {
    HashMap<String, Object> aux = new HashMap<String, Object>();
    for (int i = 0; i < stopURLs.length; i++)
      aux.put(stopURLs[i], null);
    removeStopLinks(aux);
  }

  /**
   * 删除 stop URLs。
   */
  public void removeStopLinks(Map<String, Object> stopURLs) {
    int index1;
    Iterator<Integer> it = OutLinks.keySet().iterator();
    while (it.hasNext()) {
      Integer link1 = (Integer) (it.next());
      String URL1 = (String) (IdentifyerToURL.get(link1));
      index1 = URL1.indexOf("://");
      if (index1 != -1)
        URL1 = URL1.substring(index1 + 3);
      index1 = URL1.indexOf("/");
      if (index1 != -1)
        URL1 = URL1.substring(0, index1);
      if (stopURLs.containsKey(URL1)) {
        OutLinks.put(link1, new HashMap<Integer, Double>());
        InLinks.put(link1, new HashMap<Integer, Double>());
      }
    }
  }

  public int numNodes() {
    return nodeCount;
  }
}

package xyz.anduo.crawler;

import java.util.Date;

import com.sleepycat.je.utilint.Timestamp;

/**
 * 用于限定爬虫，进行重新建模的url对象
 * 限定层次抓取，主要使用layer变量控制
 * @author anduo
 * 
 */
public class Url {
  private String oriUrl;// 原始url的值，主机部分是域名
  private String url;// URL的值，主机部分是IP，为了防止重复主机的出现
  private int urlNo;// URL的num
  private int statusCode;// 获得url相应的状态码
  private int hitNum;// 此url被其他文章引用的次数
  private String charSet;// URL对应文章的汉子编码
  private String abstractText;// 文章摘要
  private String author;// 作者
  private int weight;// 文章的权重（包含导向词的信息）
  private String description;// 文章描述
  private int fileSize;// 文章大小
  private Timestamp lastUpdataTime;// 最后修改时间
  private Date timeToLive;// 过期时间
  private String title;// 文章名称
  private String type;// 文章类型
  private String[] urlRefrence;// 引用的连接
  private int layer;// 爬取的层次，从种子开始，依次为第0层，第1层....

  // 爬虫开始的时候，种子节点层次设定为0.当抓取种子节点对应的页面时，把其中的超链接放入urlRefrence中，并把这些超链接的layer层次加1，然后再放入TODO表中
  // 之后再抓取的时候，首先应该获取layer层次，判断是否小于限制的层次，如果小于的话，就继续爬取，否则作废
  public String getOriUrl() {
    return oriUrl;
  }

  public void setOriUrl(String oriUrl) {
    this.oriUrl = oriUrl;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getUrlNo() {
    return urlNo;
  }

  public void setUrlNo(int urlNo) {
    this.urlNo = urlNo;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getHitNum() {
    return hitNum;
  }

  public void setHitNum(int hitNum) {
    this.hitNum = hitNum;
  }

  public String getCharSet() {
    return charSet;
  }

  public void setCharSet(String charSet) {
    this.charSet = charSet;
  }

  public String getAbstractText() {
    return abstractText;
  }

  public void setAbstractText(String abstractText) {
    this.abstractText = abstractText;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getFileSize() {
    return fileSize;
  }

  public void setFileSize(int fileSize) {
    this.fileSize = fileSize;
  }

  public Timestamp getLastUpdataTime() {
    return lastUpdataTime;
  }

  public void setLastUpdataTime(Timestamp lastUpdataTime) {
    this.lastUpdataTime = lastUpdataTime;
  }

  public Date getTimeToLive() {
    return timeToLive;
  }

  public void setTimeToLive(Date timeToLive) {
    this.timeToLive = timeToLive;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String[] getUrlRefrence() {
    return urlRefrence;
  }

  public void setUrlRefrence(String[] urlRefrence) {
    this.urlRefrence = urlRefrence;
  }

  public int getLayer() {
    return layer;
  }

  public void setLayer(int layer) {
    this.layer = layer;
  }



}

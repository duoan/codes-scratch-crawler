package xyz.anduo.crawler;

public interface LinkFilter {
  public boolean accept(String url);
}

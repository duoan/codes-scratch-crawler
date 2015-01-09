package xyz.anduo.crawler;

import java.util.HashSet;

public class Link {
  public String fromURL;
  public HashSet<String> toURL = new HashSet<String>();
}

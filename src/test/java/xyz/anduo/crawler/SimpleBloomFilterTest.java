package xyz.anduo.crawler;

import junit.framework.TestCase;

public class SimpleBloomFilterTest extends TestCase {
  public void test() {
    String value = "anduo@qq.com";
    SimpleBloomFilter filter = new SimpleBloomFilter();
    assertEquals(filter.contains(value), false);
    filter.add(value);
    assertEquals(filter.contains(value), true);
  }
}

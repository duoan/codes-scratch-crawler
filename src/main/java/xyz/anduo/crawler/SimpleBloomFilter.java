package xyz.anduo.crawler;

import java.util.BitSet;

import xyz.anduo.crawler.hash.SimpleHash;

public class SimpleBloomFilter {
  private static final int DEFAULT_SIZE = 2 << 24;
  private static final int[] seeds = new int[] {7, 11, 13, 31, 61,};
  private BitSet bits = new BitSet(DEFAULT_SIZE);
  private SimpleHash[] func = new SimpleHash[seeds.length];

  public SimpleBloomFilter() {
    for (int i = 0; i < seeds.length; i++) {
      func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
    }
  }

  // 覆盖方法，把URL 添加进来
  public void add(String value) {
    for (SimpleHash f : func) {
      bits.set(f.hash(value), true);
    }
  }

  // 覆盖方法，是否包含URL
  public boolean contains(String value) {
    if (value == null) {
      return false;
    }
    boolean ret = true;
    for (SimpleHash f : func) {
      ret = ret && bits.get(f.hash(value));
    }
    return ret;
  }

}

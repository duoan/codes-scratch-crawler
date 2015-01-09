package xyz.anduo.crawler.ip;

import junit.framework.TestCase;

public class IPSeekerTest extends TestCase {
  public void test() {
    IPSeeker ipSeeker = IPSeeker.getInstance();
    System.out.println(ipSeeker.getIPlLocation("202.102.86.245").country
        + ipSeeker.getIPlLocation("202.102.86.245").area);
  }
}

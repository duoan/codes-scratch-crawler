package xyz.anduo.crawler;

import junit.framework.TestCase;

public class ThreadTest extends TestCase {
  public void test() {
    for (int i = 0; i < 5; i++) {
      new MyThread(i + 1).start();
    }
  }
}


class MyThread extends Thread {
  int count = 1, number;

  public MyThread(int count) {
    number = count;
    System.out.println("创建线程:" + number);
  }

  @Override
  public void run() {
    while (true) {
      System.out.println("线程" + number + ":计数 " + count);
      if (++count == 6)
        return;
    }
  }
}

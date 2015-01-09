package xyz.anduo.crawler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PageDistance {


  /**
   * @param s1
   * @param s2
   * @return
   */
  public static <E> List<E> longestCommonSubsequence(E[] s1, E[] s2) {
    // 二维数组，初始化0
    int[][] num = new int[s1.length+1][s2.length+1];
    // 动态规划计算
    for (int i = 1; i <= s1.length; i++) {
      for (int j = 1; j <= s2.length; j++) {
        if (s1[i - 1].equals(s2[j - 1])) {
          num[i][j] = 1 + num[i - 1][j - 1];
        } else {
          num[i][j] = Math.max(num[i - 1][j], num[i][j - 1]);
        }
      }
    }
    System.out.println("length of LCS = " + num[s1.length][s2.length]);
    int s1position = s1.length, s2position = s2.length;
    List<E> result = new LinkedList<E>();
    while (s1position != 0 && s2position != 0) {
      if (s1[s1position - 1].equals(s2[s2position - 1])) {
        result.add(s1[s1position - 1]);
        s1position--;
        s2position--;
      } else if (num[s1position][s2position - 1] >= num[s1position - 1][s2position]) {
        s2position--;
      } else {
        s1position--;
      }
    }
    Collections.reverse(result);
    return result;
  }
  
  public static void main(String[] args) {
    List<String> list = PageDistance.longestCommonSubsequence(new String[]{"a","b","c","b","d","a","b"}, new String[]{"b","d","c","a","b","a"});
    for (String string : list) {
      System.out.println(string);
    }
  }
  
  
}

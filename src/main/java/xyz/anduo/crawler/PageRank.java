package xyz.anduo.crawler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * PageRank PageRank算法
 * 
 * @author anduo
 * 
 */
public class PageRank {
  private double[] rank;
  Hashtable<String, Integer> hashedPages;
  String[] sortedRank;

  public PageRank() {}

  private void rankFilter(BigMatrix dataMatrix) {
    String[] tempRank = new String[sortedRank.length];
    Boolean isEqual = true;
    // 迭代计算，直到数据收敛或者次数达到50次
    for (int i = 0; i < 50; i++) {
      rank = dataMatrix.multiply(rank);
      // 拷贝当前的数组值到临时数组
      for (int j = 0; j < sortedRank.length; j++) {
        tempRank[j] = sortedRank[j];
      }
      // 排序
      Arrays.sort(sortedRank, new compareByRank());
      // 计算是否收敛
      for (int j = 0; j < sortedRank.length; j++) {
        if (sortedRank[j].compareTo(tempRank[j]) != 0) {
          isEqual = false;
          break;
        }
      }

      if (isEqual == true) {
        break;
      } else {
        isEqual = true;
      }
    }
  }

  class compareByRank implements Comparator<String> {
    public int compare(String a, String b) {
      int indexA = hashedPages.get(a);
      int indexB = hashedPages.get(b);
      if (rank[indexA] == rank[indexB]) {
        return (0);
      } else if (rank[indexA] > rank[indexB]) {
        return (-1);
      } else {
        return (1);
      }
    }
  }

  public java.lang.String[] pageRank(java.lang.String[] s) {
    // height of data
    int theSize = Math.max(4 * s.length / 3 + 1, 16);

    // 初始化
    hashedPages = new Hashtable<String, Integer>(theSize);
    String[] pages = new String[s.length]; // theSize
    int[] nLinks = new int[s.length]; // theSize
    rank = new double[s.length];
    sortedRank = new String[s.length];
    String[] dataEntry = new String[s.length];

    // 获取数据
    for (int i = 0; i < s.length; i++) {
      String[] temp = s[i].split(" ");
      pages[i] = temp[0];
      nLinks[i] = temp.length - 1;
      sortedRank[i] = temp[0];
      rank[i] = 1;
      dataEntry[i] = "";
      hashedPages.put(pages[i], i);
    }

    int tRow, tCol;
    // 初始化矩阵
    for (int i = 0; i < s.length; i++) {
      String[] temp = s[i].split(" ");
      for (int j = 1; j < temp.length; j++) {
        tCol = hashedPages.get(temp[0]); // "to" aka row
        tRow = hashedPages.get(temp[j]); // "from" aka col
        // assumes no pages link to each other. else an if-statement is needed to check for i vs. j
        // self-linking
        dataEntry[tRow] += "{" + tCol + "," + (1 / (double) nLinks[i]) + "};";
      }
    }
    // 创建矩阵数据
    BigMatrix dataMatrix = new BigMatrix(dataEntry);
    // 排序
    rankFilter(dataMatrix);
    // 返回排序后的URL列表
    return (sortedRank);
  }
}


// 矩阵
class BigMatrix {
  public int nCols, nRows;
  EntryList[] theRows;

  // 构造函数采用String的数组作为输入，例如{"(1,1); (4,3); (5,8)", "(2,5); (3,4)","(3,8);(4,5)"}
  // 每个字符串能够初始化一行数据。例如，（2，5）表示在第二行的第二列值为5
  public BigMatrix(java.lang.String[] x) {
    nRows = x.length;
    nCols = 0;
    theRows = new EntryList[nRows];
    for (int i = 0; i < nRows; i++) {
      theRows[i] = new EntryList();
      if (x[i] != null) {
        String[] tempArr = x[i].split(";");
        if (tempArr[0] != null) {
          for (int j = 0; j < tempArr.length; j++) {
            Entry instance = new Entry(tempArr[j]);
            theRows[i].add(instance);
            if (nCols <= instance.col) {
              nCols = instance.col + 1;
            }
          }
        }
      }
    }
  }

  // 乘以1维向量
  public double[] multiply(double[] x) {
    double[] result = new double[nRows];
    for (int i = 0; i < nRows; i++) {
      EntryList temp = theRows[i];
      while ((temp != null) && (temp.data != null)) {
        result[i] += (temp.data.value * x[temp.data.col]);
        temp = temp.next;
      }
    }
    return (result);
  }
}


// 矩阵的元素元素
class Entry {
  int col;// 元素所在列
  double value;// 元素值

  public Entry(java.lang.String x) {
    String[] temp = x.split(",");
    if (temp[0].compareTo("") != 0) {
      col = Integer.parseInt(temp[0].trim().substring(1));
      value = Double.parseDouble(temp[1].trim().substring(0, temp[1].trim().length() - 1));
    }
  }
}


// 元素列表，对行进行建模
class EntryList {
  Entry data;
  EntryList next, tail;

  public EntryList() {
    next = null;
    tail = null;
    data = null;
  }

  // 添加数据
  void add(Entry x) {
    if (tail == null) {
      data = x;
      tail = this;
    } else {
      tail.next = new EntryList();
      tail.next.data = x;
      tail = tail.next;
    }
  }
}

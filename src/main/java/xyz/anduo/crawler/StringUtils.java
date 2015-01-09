package xyz.anduo.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;


public class StringUtils {

  public static double getPageSim(String urlStr1, String urlStr2) throws ParserException,
      IOException {
    ArrayList<Node> pageNodes1 = new ArrayList<Node>();
    URL url1 = new URL(urlStr1);
    Node node;
    Lexer lexer = new Lexer(url1.openConnection());
    lexer.setNodeFactory(new PrototypicalNodeFactory());
    while (null != (node = lexer.nextNode())) {
      pageNodes1.add(node);
    }

    ArrayList<Node> pageNodes2 = new ArrayList<Node>();
    URL url2 = new URL(urlStr2);
    lexer = new Lexer(url2.openConnection());
    lexer.setNodeFactory(new PrototypicalNodeFactory());
    while (null != (node = lexer.nextNode())) {
      pageNodes2.add(node);
    }

    double distance =
        PageDistance.longestCommonSubsequence(pageNodes1.toArray(), pageNodes2.toArray()).size();

    return (2.0 * distance) / ((double) pageNodes1.size() + (double) pageNodes2.size());

  }


  /**
   * 利用标题信息计算网页的内容和标题的距离
   * 
   * @param title
   * @param body
   * @return
   */
  public static double getSimiarity(String title, String body) {
    int matchNum = 0;
    for (int i = 0; i < title.length(); ++i) {
      if (body.indexOf(title.charAt(i)) >= 0) {
        ++matchNum;
      }
    }
    double score = (double) matchNum / ((double) title.length());
    return score;
  }



}

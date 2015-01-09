package xyz.anduo.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RegexTest extends TestCase {
  public void test() {
    // 任意结尾的字符串
    Pattern pattern1 = Pattern.compile("^Java.*");
    Matcher matcher1 = pattern1.matcher("Java是一门编程语言");
    boolean b1 = matcher1.matches();
    assertEquals(b1, true);

    // 以多个条件分割字符串
    Pattern pattern2 = Pattern.compile("[, |]+");
    String[] strs = pattern2.split("Java Hello World Java,Hello,,World|Sun");
    for (int i = 0; i < strs.length; i++) {
      System.out.println(strs[i]);
    }

    // 文字替换 - 首次出现的字符
    Pattern pattern3 = Pattern.compile("正则表达式");
    Matcher matcher3 = pattern3.matcher("正则表达式  Hello World,正则表达式  Hello World");
    // 替换第一个符合正则表达式的数据
    System.out.println(matcher3.replaceFirst("Java"));

    // 全部替换
    Pattern pattern4 = Pattern.compile("正则表达式");
    Matcher matcher4 = pattern4.matcher("正则表达式  Hello World,正则表达式  Hello World");
    System.out.println(matcher4.replaceAll("Java"));

    // 文字置换(置换字符)
    Pattern pattern5 = Pattern.compile("正则表达式");
    Matcher matcher5 = pattern5.matcher("正则表达式  Hello World,正则表达式  Hello World");
    StringBuffer sbr = new StringBuffer();
    while (matcher5.find()) {
      matcher5.appendReplacement(sbr, "Java");
    }
    matcher5.appendTail(sbr);
    System.out.println(sbr.toString());


    // 验证邮箱是否合法
    String email = "xyz@qq.com";
    Pattern pattern6 =
        Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", Pattern.CASE_INSENSITIVE);
    Matcher matcher6 = pattern6.matcher(email);
    System.out.println(matcher6.matches());

    // 去除html标记
    Pattern pattern7 = Pattern.compile("<.+?>", Pattern.DOTALL);
    Matcher matcher7 = pattern7.matcher("<a href=\"index.html\">主页</a>");
    String string7 = matcher7.replaceAll("");
    System.out.println(string7);
    if (matcher7.find()) {
      System.out.println(matcher7.group(1));
    }

    // 查找html中对应条件的字符串
    Pattern pattern8 = Pattern.compile("<.+?>", Pattern.DOTALL);
    Matcher matcher8 = pattern8.matcher("<a href=\"index.html\">主页</a>");
    if (matcher8.find()) {
      System.out.println(matcher8.group(1));
    }


    // 获取http://地址
    Pattern pattern9 = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
    Matcher matcher9 = pattern9.matcher("fdfsdf<http://dddd/fdfsdf>fdsfsd");
    StringBuffer sb9 = new StringBuffer();
    while (matcher9.find()) {
      sb9.append(matcher9.group());
      sb9.append("\r\n");
    }
    System.out.println(sb9.toString());


    // 替换{}中的文字
    String str10 = "java目前的发展历史是由{0}年-{1}年";
    String[][] object = {new String[] {"\\{0\\}", "1995"}, new String[] {"\\{1\\}", "2015"}};
    System.out.println(replace(str10, object));

    // 以正则条件查询指定目录下的文件



  }

  public static String replace(final String sourceString, Object[] object) {
    String tmp = sourceString;
    for (int i = 0; i < object.length; i++) {
      String[] result = (String[]) object[i];
      Pattern pattern = Pattern.compile(result[0]);
      Matcher matcher = pattern.matcher(tmp);
      tmp = matcher.replaceAll(result[1]);

    }
    return tmp;
  }


}

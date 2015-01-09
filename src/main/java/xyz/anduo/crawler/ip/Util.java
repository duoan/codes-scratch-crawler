package xyz.anduo.crawler.ip;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.log4j.Level;

import xyz.anduo.crawler.LogFactory;

/**
 * 工具类，提供一些方便的方法
 * 
 * @author anduo
 * 
 */
public class Util {
  private static StringBuilder sb = new StringBuilder();

  /**
   * 从IP的字符串形式得到字节数组形式
   * 
   * @param ip
   * @return
   */
  public static byte[] getIpByteArrayFromString(String ip) {
    byte[] ret = new byte[4];
    StringTokenizer st = new StringTokenizer(ip, ".");
    try {
      ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
      ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
      ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
      ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
    } catch (NumberFormatException e) {
      LogFactory.log("从IP的字符串形式得到字符数组形式报错", Level.ERROR, e);
    }
    return ret;
  }

  /**
   * 
   * @param ip
   * @return
   */
  public static String getIpStringFromBytes(byte[] ip) {
    sb.delete(0, sb.length());
    sb.append(ip[0] & 0xFF).append(".");
    sb.append(ip[1] & 0xFF).append(".");
    sb.append(ip[2] & 0xFF).append(".");
    sb.append(ip[3] & 0xFF);
    return sb.toString();
  }

  /**
   * 根据某种编码方式将字节数组转换成字符串
   * 
   * @param b
   * @param offset
   * @param len
   * @param encoding
   * @return
   */
  public static String getString(byte[] b, int offset, int len, String encoding) {
    try {
      return new String(b, offset, len, encoding);
    } catch (UnsupportedEncodingException e) {
      return new String(b, offset, len);
    }
  }



}

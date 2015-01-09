package xyz.anduo.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获得主机的IP地址
 * 
 * @author anduo
 * 
 */
public class IP {
  public static void main(String[] args) throws IOException {
    String hostname;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("\n");
    InetAddress ipaddress;
    hostname = input.readLine();
    try {
      ipaddress = InetAddress.getByName(hostname);
      System.out.println("IP Address : " + ipaddress.getHostAddress());
    } catch (UnknownHostException e) {
      System.out.println("Could not find IP address for : " + hostname);
    }
  }
}

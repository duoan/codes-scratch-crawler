package xyz.anduo.crawler;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DownLoadFile {

  /**
   * 根据url和网页类型生成需要保存的网页的文件名，去掉url中非文件名字符
   * 
   * @param url
   * @param contentType
   * @return
   */
  public String getFileNameByUrl(String url, String contentType) {
    // 移除http://
    url = url.substring(7);
    // text/html类型
    if (contentType.indexOf("html") != -1) {
      url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
      return url;
    }
    // 如果为application/pdf类型
    else {
      return url =
          url.replaceAll("[\\?/:*|<>\"]", "_") + "."
              + contentType.substring(contentType.lastIndexOf("/") + 1);
    }
  }

  /**
   * 保存网页字节流到本地文件
   * 
   * @param data
   * @param filePath 要保存文件的相对地址
   */
  private void saveToLocal(byte[] data, String filePath) {
    try {
      DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
      for (int i = 0; i < data.length; i++) {
        out.write(data[i]);
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public String downloadFile(String url) {
    String filePath = null;
    // 1.生成httpclient对象，并设置参数
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpget = new HttpGet(url);
    HttpResponse response;

    // 执行http get请求
    try {
      response = httpClient.execute(httpget);

      // 得到访问的状态码
      int statusCode = response.getStatusLine().getStatusCode();

      // 判断访问的状态码
      if (statusCode != HttpStatus.SC_OK) {
        System.err.println("Method faild: " + response.getStatusLine());
      }
      HttpEntity entity = response.getEntity();
      // 得到相应内容
      byte[] responseBody = EntityUtils.toByteArray(entity);

      // 根据网页url生成保存时的文件名
      filePath =
          "d:\\temp\\" + getFileNameByUrl(url, response.getFirstHeader("Content-Type").getValue());
      saveToLocal(responseBody, filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return filePath;
  }


}

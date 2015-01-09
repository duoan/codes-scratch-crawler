package xyz.anduo.crawler;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSCatWithAPI {
  public static void main(String[] args) {
    // 指定configuration
    Configuration conf = new Configuration();
    // 定义一个datainputstream
    FSDataInputStream in = null;
    try {
      // 得到文件系统的实例
      FileSystem fs = FileSystem.get(conf);
      // 通过fs的open方法打开一个指定的文件
      in = fs.open(new Path("hdfs://localhost:9000/user/myname/input/fixFontsPath.sh"));
      // 将inputstream中的内容通过IOUtils的copyBytes方法复制到system.out 中
      IOUtils.copyBytes(in, System.out, 4096, false);
      // seek到position 1
      in.seek(1);
      // 执行一 边复制一边输出工作
      IOUtils.copyBytes(in, System.out, 4096, false);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeStream(in);
    }
  }
}

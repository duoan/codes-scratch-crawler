package xyz.anduo.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.io.Text;

import com.jcraft.jsch.Buffer;

public class HBaseTest {
  private static HBaseConfiguration conf;
  static HTable table = null;

  public static void createTable(String tableName) throws IOException {
    HBaseAdmin admin = new HBaseAdmin(conf);
    if (!admin.tableExists(tableName)) {
      HTableDescriptor tableDesc = new HTableDescriptor(tableName);
      tableDesc.addFamily(new HColumnDescriptor("ip:"));
      tableDesc.addFamily(new HColumnDescriptor("time:"));
      tableDesc.addFamily(new HColumnDescriptor("type:"));
      tableDesc.addFamily(new HColumnDescriptor("cookie:"));
      // 注意这个C列，一下简单以此列来说明列存储
      tableDesc.addFamily(new HColumnDescriptor("c:"));
      admin.createTable(tableDesc);
      System.out.println("table create ok!");
    } else {
      System.out.println("table already exists!");
    }
  }

  public static void insertData() throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader("log file name"));
    if (table == null) {
      table = new HTable(conf, "tablename");
    }
    String line;
    while ((line = reader.readLine()) != null) {

    }



  }



  public static void main(String[] args) {

  }

}

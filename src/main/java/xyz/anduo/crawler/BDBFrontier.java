package xyz.anduo.crawler;

import java.util.Map.Entry;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class BDBFrontier extends AbstractFrontier implements Frontier {

  private StoredMap pendingUrisDB = null;

  public BDBFrontier(String homeDirectory) {
    super(homeDirectory);
    EntryBinding keyBinding = new SerialBinding(javaCatalog, String.class);
    EntryBinding valueBinding = new SerialBinding(javaCatalog, CrawlUrl.class);
    pendingUrisDB = new StoredMap(database, keyBinding, valueBinding, true);
  }

  /**
   * 获取下一条记录
   */
  public CrawlUrl getNext() throws Exception {
    CrawlUrl result = null;

    if (!pendingUrisDB.isEmpty()) {
      Entry<String, CrawlUrl> entry =
          (Entry<String, CrawlUrl>) pendingUrisDB.entrySet().iterator().next();
      result = entry.getValue();
      delete(entry.getKey());
    }

    return result;
  }

  /**
   * 存入url
   * 
   * @param url
   * @return
   * @throws Exception
   */
  public boolean putUrl(CrawlUrl url) throws Exception {
    put(url.getOriUrl(), url);
    return true;
  }

  /**
   * 存入数据库的方法
   * 
   * @param key
   * @param value
   */
  @Override
  protected void put(Object key, Object value) {
    pendingUrisDB.put(key, value);
  }

  /**
   * 取出
   * 
   * @param key
   * @return
   */
  @Override
  protected Object get(Object key) {
    return pendingUrisDB.get(key);
  }

  @Override
  protected Object delete(Object key) {
    return pendingUrisDB.remove(key);
  }

  /**
   * 根据url计算键值 ，可以使用各种压缩算法，包括MD5等压缩算法
   * 
   * @param url
   * @return
   */
  @SuppressWarnings("unused")
  private String caculateUrl(String url) {
    MD5Utils md5Utils = new MD5Utils();
    return md5Utils.getMD5ofStr(url);
  }

}

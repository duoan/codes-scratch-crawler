package xyz.anduo.crawler;

import java.io.File;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public abstract class AbstractFrontier {

  private Environment env;
  private static final String CLASS_CATALOG = "java_class_catalog";
  protected StoredClassCatalog javaCatalog;
  protected Database catalogdatabase;
  protected Database database;

  public AbstractFrontier(String homeDirectory) {
    EnvironmentConfig envConfig = new EnvironmentConfig();
    envConfig.setTransactional(true);
    envConfig.setAllowCreate(true);
    env = new Environment(new File(homeDirectory), envConfig);
    // 设置databaseconfig
    DatabaseConfig dbConfig = new DatabaseConfig();
    dbConfig.setTransactional(true);
    dbConfig.setAllowCreate(true);
    // 打开
    catalogdatabase = env.openDatabase(null, CLASS_CATALOG, dbConfig);
    javaCatalog = new StoredClassCatalog(catalogdatabase);
    // 设置databaseconfig
    DatabaseConfig dbConfig0 = new DatabaseConfig();
    dbConfig0.setTransactional(true);
    dbConfig0.setAllowCreate(true);
    database = env.openDatabase(null, "URL", dbConfig0);
  }

  /**
   * 关闭数据库，关闭环境
   */
  public void close() {
    database.close();
    javaCatalog.close();
    env.close();
  }

  /**
   * put方法
   * 
   * @param key
   * @param value
   */
  protected abstract void put(Object key, Object value);

  /**
   * get方法
   * 
   * @param key
   * @return
   */
  protected abstract Object get(Object key);

  /**
   * delete方法
   * 
   * @param key
   * @return
   */
  protected abstract Object delete(Object key);
}

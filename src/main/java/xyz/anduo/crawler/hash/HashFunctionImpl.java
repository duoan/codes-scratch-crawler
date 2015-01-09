package xyz.anduo.crawler.hash;


/**
 * hash算法实现类
 * 
 * @author anduo
 * 
 */
public class HashFunctionImpl implements HashFunction {

  /**
   * MurMurHash算法，是非加密HASH算法，性能很高，碰撞率低
   */
  public Long hash(String key) {
    return MurmurHash.hash64(key);
    // ByteBuffer buf = ByteBuffer.wrap(key.toString().getBytes());
    // int seed = 0x1234ABCD;
    //
    // ByteOrder byteOrder = buf.order();
    // buf.order(ByteOrder.LITTLE_ENDIAN);
    //
    // long m = 0xc6a4a7935bd1e995L;
    // int r = 47;
    //
    // long h = seed ^ (buf.remaining() * m);
    //
    // long k;
    // while (buf.remaining() >= 8) {
    // k = buf.getLong();
    //
    // k *= m;
    // k ^= k >>> r;
    // k *= m;
    //
    // h ^= k;
    // h *= m;
    // }
    //
    // if (buf.remaining() > 0) {
    // ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
    // finish.put(buf).rewind();
    // h ^= finish.getLong();
    // h *= m;
    // }
    //
    // h ^= h >>> r;
    // h *= m;
    // h ^= h >>> r;
    //
    // buf.order(byteOrder);
    // return h;
  }

}

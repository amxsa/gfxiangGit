package com.gf.imsCommon.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Redis数据访问封装的方法接口
 * 不同的redis访问方式实现此接口
 * @file RedisClient.java
 *
 */
public interface JedisClient {
   /**
	* 判断key是否存在
	* @param key
	* @return
   */
   boolean keyExits(String key);
   /**
	* 判断key是否存在
	* @param key
	* @return
  */
  boolean keyExitsSerializable(Serializable key);
   /**
    * 删除key
    * @param key
    */
   void keyDel(String key);
   /**
    * 删除key
    * @param key
    */
   void keyDelSerializable(Serializable key);
   /**
    * 删除当前选择数据库中的所有key
    */
   void flushdb();
   /**
    * 删除所有数据库中的所有key
    */
   void flushall();
   /**
    * String操作  set
    * @param key
    * @param value
    */
   void set(String key,String value);
   /**
    * String操作  set,存储可序列化java对象
    * @param key
    * @param value
    */
   void setSerializable(Serializable key,Serializable value);
   /**
    * String操作  set，支持设置有效时间
    * @param key
    * @param seconds 有效时间，单位为秒
    * @param value
    */
   void setex(String key,int seconds,String value);
   /**
    * String操作  set，支持设置有效时间,存储可序列化java对象
    * @param key
    * @param seconds 有效时间，单位为秒
    * @param value
    */
   void setexSerializable(Serializable key,int seconds,Serializable value);
  /**
   * String操作  get
   * @param key
   * @return
   */
   String get(String key);
   /**
    * String操作  get,获取可序列化java对象
    * @param key
    * @return
    */
   Serializable getSerializable(Serializable key);
   /**
    * String操作  incr，名称为key的string增1操作,如果key不存在，则先设置key,再自增;
    * 有可能抛出异常，因为可能对一个非数值类型自增操作
    * @param key
    * @return 返回自增后key对应的值
    */
   Long incr(String key) throws Exception;
   /**
    * String操作  incrby，名称为key的string增加integer，,如果key不存在，则先设置key,再自增
    * 有可能抛出异常，因为可能对一个非数值类型自增操作
    * @param key
    * @param num 自增的数值，如果是负数，则变成自减
    * @return 返回自增后key对应的值
    */
   Long incrby(String key, long num)throws Exception;
   /**
    * List操作 llen：返回名称为key的list的长度
    * @param key
    * @return
    */
   long llen(Serializable key);
   /**
    * List操作 rpush：在名称为key的list尾添加一个值为value的元素
    * @param key
    * @param value
    * @return 返回列表的长度
    */
   long rpush(Serializable key, Serializable value);
   /**
    * List操作 lpush：在名称为key的list头添加一个值为value的 元素
    * @param key
    * @param value
    * @return 返回列表的长度
    */
   long lpush(Serializable key, Serializable value);
   /**
    * List操作 lpop：返回并删除名称为key的list中的首元素
    * @param key
    * @return 返回列表的长度
    */
   Serializable lpop(Serializable key);
   /**
    * 返回并删除名称为key的list中的尾元素
    * @param key
    * @return 返回列表的长度
    */
   Serializable rpop(Serializable key);
   /**
    * List操作 lindex：返回名称为key的list中index位置的元素
    * @param key
    * @param index 
    * @return
    */
   Serializable lindex(Serializable key, int index);
   /**
    * List操作 lindex：给名称为key的list中index位置的元素赋值
    * @param key
    * @param index
    * @param value
    */
   void lset(Serializable key, int index, Serializable value);
   /**
    * List操作 lindex：删除count个key的list中值为value的元素
    * @param key
    * @param count
    * @param value
    */
   void lrem(Serializable key, int count, Serializable value);
   /**
    * List操作 lrange：返回名称为key的list中start至end之间的元素
    * @param key
    * @param start
    * @param end
    */
   List<Serializable> lrange(Serializable key, int start, int end);
   /**
    *  Set 操作 sadd：在set中加入元素
    * @param sKey redis中set的key
    * @param value 元素
    */
   void sadd(String sKey,String value);
   /**
    *  Set 操作 sadd：在set中删除元素
    * @param sKey redis中set的key
    * @param value 元素
    */
   void srem(String sKey,String value);
   /**
    * Set 操作 smembers：获取set中所有的的value
    * @param sKey redis中set的key
    * @return
    */
   Set<String> smembers(String sKey);
   /**
    * 判断 value 是否是sKey集合的元素  
    * @param sKey
    * @param value
    * @return
    */
   boolean sismember(String sKey,String value);
   /**
    * 获取一个key的ttl
    * @param key
    * @return
    */
   long ttl(String key);
   
   
     
     
}

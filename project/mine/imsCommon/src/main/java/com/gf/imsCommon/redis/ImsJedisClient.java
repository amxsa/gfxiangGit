package com.gf.imsCommon.redis;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
/**
 * redis客户端实现类
 * 无分片功能
 * @author laizs
 * @time 2015-2-3下午1:36:40
 * @file ShardRedisClient.java
 *
 */
public class ImsJedisClient /*implements JedisClient*/ {
	
	private final static Logger logger=Logger.getLogger(ImsJedisClient.class);
	/**
	 * redid连接池配置
	 */
//    private JedisPoolConfig poolConfig; 
//    /**
//     * redis分片的连接池
//     */
//    private JedisPool jedisPool ;
//	/**
//	 * 无参数构造函数
//	 */
//	public ImsJedisClient(JedisPoolConfig poolConfig,String serverHost,int serverPort,int timeout,String password){
//		logger.info("---------------------------");
//		logger.info("正在初始化 Redis数据库连接池：host->"+serverHost+",port->"+serverPort+",password->"+password+",timeout->"+timeout);
//        // 构造池 
//        jedisPool = new JedisPool(poolConfig, serverHost, serverPort, timeout, password);
//	}
//	//getter and setter
//	public JedisPool getJedisPool() {
//		return jedisPool;
//	}
//	public void setJedisPool(JedisPool jedisPool) {
//		this.jedisPool = jedisPool;
//	}
//	/**
//	 * 将使用的事例归还连接池
//	 * @param jedis
//	 */
//	private void returnResource(Jedis jedis){
//		if(null!=jedis){
//			this.jedisPool.returnResource(jedis);
//		}
//	}
//	
//	public boolean keyExits(String key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.exists(key);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public boolean keyExitsSerializable(Serializable key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.exists(SerializeUtil.serialize(key));
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public void keyDel(String key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.del(key);
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public void keyDelSerializable(Serializable key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.del(SerializeUtil.serialize(key));
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//
//	
//	public void flushdb() {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.flushDB();
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//
//	
//	public void flushall() {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.flushAll();
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public void set(String key, String value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.set(key, value);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public void setSerializable(Serializable key,Serializable value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public void setex(String key, int seconds, String value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.setex(key, seconds, value);
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public void setexSerializable(Serializable key,int seconds,Serializable value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.setex(SerializeUtil.serialize(key), seconds, SerializeUtil.serialize(value));
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public String get(String key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.get(key);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public Serializable getSerializable(Serializable key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			byte[] data= jedis.get(SerializeUtil.serialize(key));
//			return SerializeUtil.unserialize(data);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public Long incr(String key) throws Exception{
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.incr(key);
//		}catch (Exception e) {
//			throw e;
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public Long incrby(String key, long num) throws Exception{
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			 return jedis.incrBy(key, num);
//		}catch (Exception e) {
//			throw e;
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public long llen(Serializable key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			 return jedis.llen(SerializeUtil.serialize(key));
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public long rpush(Serializable key, Serializable value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.rpush(SerializeUtil.serialize(key),SerializeUtil.serialize(value));
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public long lpush(Serializable key, Serializable value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.lpush(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public Serializable lpop(Serializable key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			byte[] data= jedis.lpop(SerializeUtil.serialize(key));
//			return SerializeUtil.unserialize(data);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public Serializable rpop(Serializable key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			byte[] data= jedis.rpop(SerializeUtil.serialize(key));
//			return SerializeUtil.unserialize(data);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public Serializable lindex(Serializable key, int index) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			byte[] data= jedis.lindex(SerializeUtil.serialize(key), index);
//			return SerializeUtil.unserialize(data);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public void lset(Serializable key, int index, Serializable value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.lset(SerializeUtil.serialize(key), index, SerializeUtil.serialize(value));
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public void lrem(Serializable key, int count, Serializable value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.lrem(SerializeUtil.serialize(key), count, SerializeUtil.serialize(value));
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public List<Serializable> lrange(Serializable key, int start, int end) {
//		Jedis jedis=null;
//		List<Serializable> datas=new ArrayList<Serializable>();
//		try {
//			jedis= this.jedisPool.getResource();
//			List<byte[]> bytesList=jedis.lrange(SerializeUtil.serialize(key), start, end);
//			if(null!=bytesList && bytesList.size()>0){
//				for(byte[] temp:bytesList){
//					datas.add(SerializeUtil.unserialize(temp));
//				}
//			}
//			return datas;
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public void sadd(String sKey, String value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.sadd(sKey, value);
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public void srem(String sKey, String value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			jedis.srem(sKey, value);
//		}
//		finally{
//			returnResource(jedis);
//		}
//		
//	}
//	
//	public Set<String> smembers(String sKey) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.smembers(sKey);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public boolean sismember(String sKey, String value) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.sismember(sKey, value);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
//	
//	public long ttl(String key) {
//		Jedis jedis=null;
//		try {
//			jedis= this.jedisPool.getResource();
//			return jedis.ttl(key);
//		}
//		finally{
//			returnResource(jedis);
//		}
//	}
}

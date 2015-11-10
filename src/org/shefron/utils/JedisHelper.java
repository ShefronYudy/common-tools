package org.shefron.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisHelper {
	
	private static final Log log = LogFactory.getLog(JedisHelper.class);
	  private JedisPool jedisPool;
	  private JedisPoolConfig jedisPoolConfig;
	  private String redisHost;
	  private int redisPort = 6379;
	  private int timeout = 2000;
	  private String password;
	  private int database = 0;
	  private static final int DEFAULT_MAX_TRY = 5;
	  
	  public JedisHelper() {}
	  
	  public JedisHelper(JedisPoolConfig jedisPoolConfig, String redisHost, int redisPort)
	  {
	    this(jedisPoolConfig, redisHost, redisPort, 2000, null, 0);
	  }
	  
	  public JedisHelper(JedisPoolConfig jedisPoolConfig, String redisHost, int redisPort, String password)
	  {
	    this(jedisPoolConfig, redisHost, redisPort, 2000, password, 0);
	  }
	  
	  public JedisHelper(JedisPoolConfig jedisPoolConfig, String redisHost, int redisPort, int timeout, String password, int database)
	  {
	    this.jedisPoolConfig = jedisPoolConfig;
	    this.redisHost = redisHost;
	    this.redisPort = redisPort;
	    this.timeout = timeout;
	    this.password = password;
	    this.database = database;
	    init();
	  }
	  
	  public void init()
	  {
	    this.jedisPool = new JedisPool(this.jedisPoolConfig, this.redisHost, this.redisPort, this.timeout, this.password, this.database);
	  }
	  
	  public void destory()
	  {
	    this.jedisPool.destroy();
	  }
	  
	  public Jedis getResource()
	  {
	    return getResource(DEFAULT_MAX_TRY);
	  }
	  
	  public Jedis getResource(int maxTry)
	  {
	    Jedis jedis = null;
	    try
	    {
	      jedis = (Jedis)this.jedisPool.getResource();
	    }
	    catch (Exception e)
	    {
	      log.error("error while get jedis resource: [ip: " + this.redisHost + ", port: " + this.redisPort + "]", e);
	    }
	    if ((jedis == null) || (!jedis.isConnected()))
	    {
	      if (jedis != null) {
	        this.jedisPool.returnBrokenResource(jedis);
	      }
	      synchronized (this.jedisPool)
	      {
	        try
	        {
	          this.jedisPool.wait(1000L);
	        }
	        catch (InterruptedException e)
	        {
	          e.printStackTrace();
	          return null;
	        }
	      }
	      if (maxTry > 0) {
	        return getResource(--maxTry);
	      }
	      return null;
	    }
	    return jedis;
	  }
	  
	  public void returnResource(Jedis resource)
	  {
	    this.jedisPool.returnResource(resource);
	    synchronized (this.jedisPool)
	    {
	      this.jedisPool.notify();
	    }
	  }
	  
	  public void returnBrokenResource(Jedis resource)
	  {
	    this.jedisPool.returnBrokenResource(resource);
	  }
	  
	  public JedisPoolConfig getJedisPoolConfig()
	  {
	    return this.jedisPoolConfig;
	  }
	  
	  public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig)
	  {
	    this.jedisPoolConfig = jedisPoolConfig;
	  }
	  
	  public String getRedisHost()
	  {
	    return this.redisHost;
	  }
	  
	  public void setRedisHost(String redisHost)
	  {
	    this.redisHost = redisHost;
	  }
	  
	  public int getRedisPort()
	  {
	    return this.redisPort;
	  }
	  
	  public void setRedisPort(int redisPort)
	  {
	    this.redisPort = redisPort;
	  }
	  
	  public int getTimeout()
	  {
	    return this.timeout;
	  }
	  
	  public void setTimeout(int timeout)
	  {
	    this.timeout = timeout;
	  }
	  
	  public String getPassword()
	  {
	    return this.password;
	  }
	  
	  public void setPassword(String password)
	  {
	    this.password = password;
	  }

}

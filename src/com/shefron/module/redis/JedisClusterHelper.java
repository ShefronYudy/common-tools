package com.shefron.module.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterHelper {
	
	
	
	public static void main(String[] args) throws IOException{
		JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(20);  
        config.setMaxIdle(2);  
  
        HostAndPort hp0 = new HostAndPort("localhost", 7000);  
        HostAndPort hp1 = new HostAndPort("localhost", 7001);  
        HostAndPort hp2 = new HostAndPort("localhost", 7002);  
        HostAndPort hp3 = new HostAndPort("localhost", 7003);  
        HostAndPort hp4 = new HostAndPort("localhost", 7004);  
        HostAndPort hp5 = new HostAndPort("localhost", 7005);  
  
        Set<HostAndPort> hps = new HashSet<HostAndPort>();  
        hps.add(hp0);  
        hps.add(hp1);  
        hps.add(hp2);  
        hps.add(hp3);  
        hps.add(hp4);  
        hps.add(hp5);  
  
        // 超时，最大的转发数，最大链接数，最小链接数都会影响到集群  
        JedisCluster jedisCluster = new JedisCluster(hps, 5000, 10, config);
        
        long start = System.currentTimeMillis();  
        for (int i = 0; i < 100; i++) {  
            jedisCluster.set("sn" + i, "n" + i);  
        }  
        long end = System.currentTimeMillis();  
  
        System.out.println("Simple  @ Sharding Set : " + (end - start) / 10000);  
  
        for (int i = 0; i < 1000; i++) {  
            System.out.println(jedisCluster.get("sn" + i));  
        }  
  
        jedisCluster.close();  
	}

}

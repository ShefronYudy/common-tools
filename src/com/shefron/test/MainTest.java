package com.shefron.test;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.shefron.module.redis.JedisHelper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import sun.misc.BASE64Decoder;

/**
 * Created by Administrator on 2014/11/22.
 */
public class MainTest {
	
	private static HashMap<String,String> map = new HashMap<String,String>(){
		{
			put("","");
		}
	};
	

    public void testF(){
        File dir = new File("E:/temp/res");
        int i=1;
        for(File file : dir.listFiles()){
//            if(file.getName().indexOf("_") != -1){
//                String name = file.getName();//1_11.jpg
//                int seq = Integer.parseInt(name.substring(2,name.lastIndexOf(".")) );
                System.out.println(file.getName());
                File newFile = new File(dir,"26_"+(i++)+".jpg" );
                file.renameTo(newFile);
//            }
        }
    }

    public void testCpuNum(){
        System.out.println("CPU 个数："+Runtime.getRuntime().availableProcessors() );
    }

    public void testBase64(){
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            String content = "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";
            String tempStr = new String(content.getBytes("UTF-8"),"GBK");
            byte[] bytes = decoder.decodeBuffer(tempStr);

            System.out.println("#"+new String(bytes,0,bytes.length) );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testImg(){
        File file = new File("E:/temp/res/2.jpg");
        try {
            ImageIO.read(file);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
    public void testOneYearMsecs(){
    	//一年的毫秒数
    	System.out.println((long)(365.2425 * (24*60*60*1000)) );
    }
    
    public void testRedisPassword(){
    	String redisPassword = "Boco$bomc222";
    	
    	JedisHelper jedisHelper = new JedisHelper(new JedisPoolConfig(), "127.0.0.1", 6379, redisPassword);
    	Jedis jedis = jedisHelper.getSingleClient();
    	
    	Long flag = jedis.rpush("bomc_key", "test-value");
    	Long flag2 = jedis.rpush("bomc_key", "test-value2");
    	System.out.println("flag:"+flag +" "+flag2);

    	String bomc_key = jedis.lpop("bomc_key");
    	System.out.println("value:"+bomc_key);
    }
    
    private class SpoolDirectoryRunnable implements Runnable{
    	private String[] keys;
    	
    	public SpoolDirectoryRunnable(String[] keys){
    		this.keys = keys;
    	}

		@Override
		public void run() {
			System.out.println("begin ....");
			System.out.println("keys = "+Arrays.asList(this.keys) );
			try{
				while(!Thread.interrupted() ){
					Thread.sleep(3*1000);
					System.out.println("end ....");
					this.keys[0] = "3";
					this.keys[1] = "4";
					System.out.println("keys = "+Arrays.asList(this.keys) );
					int i = 2/0;
				}
			}catch(Exception e){
				System.out.println("error....");
//				e.printStackTrace();
				this.keys = new String[]{"1","2"};
//				Throwables.propagate(e);
			}
		}
    }
    
    private String[] keys = new String[2];
    
    public void testExecutor(){
    	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    	keys[0] = "1";
    	keys[1] = "2";
    	Runnable runner = new SpoolDirectoryRunnable(keys);
		executor.scheduleWithFixedDelay(runner, 0, 500, TimeUnit.MILLISECONDS);
		
		try{
			Thread.sleep(20*1000);
			System.out.println("The End!");
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    private int timePoint = 0;
    private long syncCycle = 5*60*1000;
    private int currTimePoint = 38;
    private long currTimeMillis = System.currentTimeMillis()-6*60*1000;
    
    private boolean isTimePoint(){
		//检查调用周期
		long currTime = -1;
		int currMin;
		Calendar today = Calendar.getInstance();
		currTime = System.currentTimeMillis();
		today.setTimeInMillis(currTime);
		currMin = today.get(Calendar.MINUTE);
		System.out.println("currMin = " + currMin);
		if(currMin == this.currTimePoint){
			return false;
		}
		
		int _timePoint = this.timePoint;
		int _intval = (int)syncCycle/1000/60;
		int _sum = 60/_intval;
		for(int i=0; i<_sum; i++){
			_timePoint += (i==0?0:_intval);
			_timePoint = (_timePoint>=60?_timePoint%60:_timePoint);
			if(currMin == _timePoint){
				System.out.println("timePoint : "+_timePoint+" done!");
				this.currTimePoint = _timePoint;
				this.currTimeMillis = System.currentTimeMillis();
				return true;
			}
		}
		long dltTime = System.currentTimeMillis() - this.currTimeMillis;
		if(dltTime > this.syncCycle){
			long n = dltTime/this.syncCycle;
			this.currTimePoint += n*_intval;
			this.currTimePoint = this.currTimePoint%60;
			this.currTimeMillis = System.currentTimeMillis();
			return true;
		}
		
		return false;
	}
    
    public void testTimePoint(){
    	while(true){
    		while(!isTimePoint()){
        		try{
        			Thread.sleep(1000);
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}
    		
    		try{
    			Thread.sleep(100);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		System.out.println("执行完毕：");
    	}
    	
    }
    
	private long[] splitTotalTime(int num,long totalTime){
		Random random = new Random();
    	long[] points = new long[num];
    	long _totalTime = totalTime;
    	for(int i=0; i < num; i++){
    		int seed = 0;
    		if(i == 0){
    			seed = (int)(_totalTime/2);
    		}else{
    			_totalTime -= points[i-1];
    			seed = (int)(_totalTime/2);
    		}
    		points[i] = random.nextInt(seed);
    	}
    	
    	points[num-1] = totalTime;
    	for(int i=0; i < (num-1); i++){
    		points[num-1] -= points[i];
    	}
    	return points;
	}
    
    public void splitTime(){
    	//[974, 606, 381, 385]
    	System.out.println(Arrays.toString(splitTotalTime(6, 2346)));
    }
    
    @Test
    public void testScheduledExecutorService(){
    	final java.util.concurrent.ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("handle ...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("done.");
			}
		};
		executor.schedule(runnable, 10, TimeUnit.SECONDS);
		executor.shutdown();
//		try {
//			//block until 30 secs later
//			executor.awaitTermination(30, TimeUnit.SECONDS);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		
		int i = 0;
		while(true){
			try {
				i++;
				System.out.println("#shutdown:"+executor.isShutdown());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i==80){
				break;
			}
		}
		
    }
    


}

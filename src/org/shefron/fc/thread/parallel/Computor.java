package org.shefron.fc.thread.parallel;

import java.util.Random;

public class Computor {

	private boolean stop = false;


	public int compute(){
		long startMills = System.currentTimeMillis();
		//Ïàµ±ºÄCPU
		while(!stop){
			if( System.currentTimeMillis()>(startMills+10*1000) )
				break;
		}

		return new Random().nextInt()%10;
	}

}

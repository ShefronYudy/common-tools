package org.shefron.fc.thread.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ExecutorService exec = Executors.newCachedThreadPool();

		List<Computor> list = new ArrayList<Computor>();
		for (int i=0;i<20;i++){
			list.add(new Computor() );
		}
		Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

		parallelResults(exec,list,queue);

		exec.shutdown();

		try {
			System.out.println("flag:"+exec.awaitTermination(9, TimeUnit.SECONDS) );


			for (Integer in : queue){
System.out.println("seq = "+ in);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

	public static void parallelResults(Executor exec,List<Computor> list,final Queue queue){
		for (final Computor c : list){
			exec.execute(new Runnable(){
				@Override
				public void run() {
					queue.add(new Integer(c.compute()) );
				}
			});
			System.out.println("add");
		}
	}

}

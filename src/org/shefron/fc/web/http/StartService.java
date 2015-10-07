package org.shefron.fc.web.http;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class StartService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HttpServer httpSer = HttpServer.create(new InetSocketAddress(6060),
					0);

			httpSer.createContext("/myApp", new BusiHandler());

			httpSer.setExecutor(Executors.newCachedThreadPool());

			httpSer.start();

		} catch (Exception e) {

		}
	}

}

package org.shefron.fc.classloader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;


public class Test {


	public static void main(String[] args){
		URL url = Test.class.getResource("/resouce/udp-dispatcher.jar");

		try {

//			System.out.println("path="+url.getPath() );
			Class<?> _class = LoadClass.getClassInJar(url.getPath(), "com.boco.trap.test.TestUnit");
			Object obj = _class.newInstance();
			Method method = _class.getDeclaredMethod("testRandom", new Class[]{});
			method.invoke(obj, new Object[]{});

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}


}

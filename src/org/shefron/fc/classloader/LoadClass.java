package org.shefron.fc.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoadClass {

	public static Class<?> getClassInJar(URL jarPath, String className)
			throws ClassNotFoundException {

		URLClassLoader urlLoader = new URLClassLoader(
				new URL[] {jarPath},
				System.class.getClassLoader() );

		return urlLoader.loadClass(className);

	}


	public static Class<?> getClassInJar(String jarPath,String className) throws IOException{

		JarFile jarfile = new JarFile(jarPath);
		Enumeration<JarEntry> entrys = jarfile.entries();

		JarEntry entry = null;
		String _name = className.replace('.', '/')+".class";
		while(entrys.hasMoreElements() ){
			entry = entrys.nextElement();

			if(entry.getName().equals(_name) ){

				InputStream in = jarfile.getInputStream(entry);

				CustomClassLoader classLoader = new CustomClassLoader();
				return classLoader.findClass(className, in);
			}
		}

		return null;

	}


}

package org.shefron.fc.classloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader{
	private String basePath;

	public CustomClassLoader(){
		this(null);
	}

	public CustomClassLoader(String basePath){
		this.basePath = basePath;
	}

	public CustomClassLoader(ClassLoader classLoader,String basePath){
		super(classLoader);
		this.basePath = basePath;
	}

	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		byte classBytes[];

		classBytes = getTypeByPath();

		if(classBytes == null){
			throw new ClassNotFoundException(className + "not found!");
		}

		return defineClass(className, classBytes, 0, classBytes.length);
	}

	public Class<?> findClass(String className,InputStream in){
		BufferedInputStream bis = new BufferedInputStream(in);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
			try {
				int b = bis.read();
				while(b != -1){
					bout.write(b);
					b = bis.read();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return defineClass(className, bout.toByteArray(), 0, bout.toByteArray().length);

	}

	private byte[] getTypeByPath(){
		String path = this.basePath + File.separatorChar + this.basePath.replace('.', File.separatorChar)+".class";

		try {
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();

			int b = bis.read();
			while(b != -1){
				bout.write(b);

				b = bis.read();
			}

			return bout.toByteArray();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}



}

package com.shefron.module.codec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xerial.snappy.Snappy;

public class Snappy4Test {

	public static void main(String[] args) {
		long startT = System.currentTimeMillis();
		System.out.println(Snappy.getNativeLibraryVersion());
		String filePath = "E:\\系统类\\cn_windows_10_enterprise_version_1511_x64_dvd_7224788.iso";
		String filePath2 = "E:\\系统类\\test.tmp";
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(filePath);
			fos = new FileOutputStream(filePath2);
			byte[] bytes = new byte[8192];
			byte[] newBytes =  null;
			while (fis.read(bytes) != -1){
				newBytes = Snappy.compress(bytes);
				fos.write(newBytes, 0, newBytes.length);
			}
			long endT = System.currentTimeMillis();
			System.out.println("done! cost= "+(endT-startT) +" ms");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

package org.shefron.fc.zip;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\a.txt"));

			// BufferedReader _br = new BufferedReader(new InputStreamReader(new
			// FileInputStream("D:\\a.txt"), "GBK"));

			BufferedOutputStream bos = new BufferedOutputStream(
					new GZIPOutputStream(new FileOutputStream("D:\\test.gz")));
			int c = 0;
			while ((c = br.read()) != -1) {// ¶Áµ¥¸ö×Ö·û£¬cÎªintµÄ×Ö·û±àÂë
				bos.write(c);
			}
			br.close();
			bos.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new GZIPInputStream(new FileInputStream("D:\\test.gz"))));
			String s = "";
			while ((s = reader.readLine()) != null) {
				System.out.println("#" + s);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

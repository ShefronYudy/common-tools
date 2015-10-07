package org.shefron.fc.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipMain {

	public static void f(String... strings) {
		System.out.println("#" + Arrays.toString(strings));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Ð´
			BufferedReader br = new BufferedReader(new FileReader(
					"D:\\test\\a.txt"));
			BufferedReader br2 = new BufferedReader(new FileReader(
					"D:\\test\\b.txt"));

			FileOutputStream fos = new FileOutputStream("D:\\test.zip");

			CheckedOutputStream checkSum = new CheckedOutputStream(fos,
					new Adler32());

			ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(
					checkSum));

			zip.setComment("a test!!!");

			ZipEntry zipE = new ZipEntry("D:\\test\\a.txt");
			ZipEntry zipE2 = new ZipEntry("D:\\test\\b.txt");

			zip.putNextEntry(zipE);
			int c = 0;
			while ((c = br.read()) != -1) {// ¶Áµ¥¸ö×Ö·û£¬cÎªintµÄ×Ö·û±àÂë
				zip.write(c);
			}
			zip.putNextEntry(zipE2);
			int c2 = 0;
			while ((c2 = br2.read()) != -1) {// ¶Áµ¥¸ö×Ö·û£¬cÎªintµÄ×Ö·û±àÂë
				zip.write(c2);
			}
			br.close();
			br2.close();
			zip.close();
			System.out
					.println("#checkSum:" + checkSum.getChecksum().getValue());

			// ¶Á
			FileInputStream reader = new FileInputStream("D:\\test.zip");
			CheckedInputStream checkInSum = new CheckedInputStream(reader,
					new Adler32());
			ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(
					checkInSum));

			ZipEntry ze = null;
			System.out.println("#checkInSum:"
					+ checkInSum.getChecksum().getValue());
			while ((ze = zipIn.getNextEntry()) != null) {
				System.out.println("#file:" + ze);
				int c21 = 0;
				while ((c21 = zipIn.read()) != -1) {
					System.out.write(c21);
				}
			}
			reader.close();
			zipIn.close();

			// zip files
			ZipFile zipFile = new ZipFile("D:\\test.zip");

			@SuppressWarnings("rawtypes")
			Enumeration enum2 = zipFile.entries();
			while (enum2.hasMoreElements()) {
				ZipEntry ze4 = (ZipEntry) enum2.nextElement();
				System.out.println("ze4:" + ze4);
			}
			zipFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

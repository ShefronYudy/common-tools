package org.shefron.fc.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class RedireactConsoleout {

	public static void main(String[] args) {
		try {

			File file = new File("D:\\a.txt");
			if (file.exists()) {
				file.mkdirs();
			}

			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			PrintStream ps = new PrintStream(new BufferedOutputStream(
					new FileOutputStream("D:\\test.out")));

			System.setErr(ps);
			System.setIn(in);
			System.setOut(ps);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s = "";
			while ((s = br.readLine()) != null) {
				System.out.println(s);
			}
			System.out.println("把系统控制台输出流进行指向！");

			ps.close();
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

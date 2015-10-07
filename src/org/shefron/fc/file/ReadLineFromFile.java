package org.shefron.fc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Set;

public class ReadLineFromFile {

	/**
	 * ��ȡĳһ��
	 */
	public static String readLine(File file, long line) throws Exception {
		return subLines(file, line, line);
	}

	/**
	 * �ӿ�ʼλ�û�ȡָ�����ȵ�����
	 * 
	 * @param file
	 * @param startPos
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String getFixedLines(File file, long startPos, long length)
			throws Exception {
		return subLines(file, startPos, (startPos + length));
	}

	public static String getFixedLines(File file, long[] lines) {
		Set<Long> tmpLines = new HashSet<Long>();
		for (long line : lines) {
			if (line >= 0 && line <= getTotalLines(file)) {
				tmpLines.add(new Long(line));
			}
		}

		StringBuffer buffer = new StringBuffer();

		String s = null;
		try {
			LineNumberReader reader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(file), "UTF-8"));

			long linePos = 0;
			while ((s = reader.readLine()) != null) {
				linePos++;
				if (tmpLines.contains(new Long(linePos))) {
					buffer.append(s + "\n");
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return buffer.toString();

	}

	/**
	 * ȡ���ļ���ָ������������
	 * 
	 * @param file
	 * @param startLine
	 * @param endLine
	 * @return
	 * @throws Exception
	 */
	public static String subLines(File file, long startLine, long endLine)
			throws Exception {
		if (startLine > endLine) {
			throw new Exception("�����Ƿ�");
		}
		StringBuffer buffer = new StringBuffer();

		String s = null;
		try {
			long totals = getTotalLines(file);

			if (startLine < 0) {
				startLine = 0;
			}

			if (endLine > totals) {
				endLine = totals;
			}

			LineNumberReader reader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(file), "UTF-8"));

			/** ���õ�ǰ�к�,�˺��ȡʱ�ڴ˻����ϵ��� */
			// reader.setLineNumber(5);

			long linePos = 0;
			while ((s = reader.readLine()) != null) {
				linePos++;

				if (linePos >= startLine && linePos <= endLine) {
					System.out.println("lineNum:" + reader.getLineNumber());
					buffer.append(s + "\n");
				}

				if (linePos > endLine) {
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return buffer.toString();
	}

	/**
	 * ��ȡ�ļ�������
	 * 
	 * @param file
	 * @return
	 */
	private static long getTotalLines(File file) {
		long lines = 0;
		try {
			FileReader in = new FileReader(file);
			LineNumberReader reader = new LineNumberReader(in);
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return lines;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("E:/temp/allci.xml");

		try {
			System.out.println(ReadLineFromFile.getFixedLines(file, new long[] {
					12, 15, 18, 21, 20000 }));
			System.out.println("------------------------------");
			System.out.println(ReadLineFromFile.subLines(file, 10L, 15L));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

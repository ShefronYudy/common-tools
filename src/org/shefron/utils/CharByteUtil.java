package org.shefron.utils;

import java.util.Arrays;

public class CharByteUtil {

	/**
	 * 1个字符两字节16位
	 * 
	 * @param c
	 * @return
	 */
	public static byte[] charTo2Byte(char c) {
		int i = c;
		/** b1为低8位值 */
		byte b1 = (new Integer(i & 0xff)).byteValue();// i和0xff低8位与计算
		i >>= 8;// 右移8位
		/** b2为高8为值 */
		byte b2 = (new Integer(i & 0xff)).byteValue();
		return new byte[] { b2, b1 };
	}

	/**
	 * 两个字节代表一个字符
	 * 
	 * @param _2bytes
	 * @return
	 */
	public static char _2ByteToChar(byte[] _2bytes) {
		byte byte1 = _2bytes[0];
		byte byte2 = _2bytes[1];
		byte1 <<= 8;
		return (char) (byte1 + byte2);
	}

	public static byte[] char2Bytes(char[] charArray) {
		byte[] bytes = new byte[2 * charArray.length];
		int i = 0;
		for (char ch : charArray) {
			System.arraycopy(charTo2Byte(ch), 0, bytes, i * 2, 2);
			i++;
		}
		return bytes;
	}

	public static char[] byte2Chars(byte[] bytes) {
		char[] chars = new char[bytes.length / 2];

		int j = 0;
		for (int i = 1; i < bytes.length; i += 2) {
			chars[j++] = _2ByteToChar(new byte[] { bytes[i - 1], bytes[i] });
		}

		return chars;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cn = "ABC";
		char[] chars = cn.toCharArray();
		System.out.println(Arrays.toString(chars));

		for (byte by : char2Bytes(chars)) {
			System.out.println("B=" + by);
		}
		System.out.println("-----------");

		System.out.println(Arrays.toString(byte2Chars(char2Bytes(chars))));

	}

}

package org.shefron.utils;

import java.util.Arrays;

public class Native2AsciiUtils {

	private static String PREFIX = "\\u";
	private static char[] hex = "0123456789ABCDEF".toCharArray();

	public static String native2Ascii(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			sb.append(char2Ascii(chars[i]));
		}
		return sb.toString();
	}

	/**
	 * Native character to ascii string.
	 *
	 * @param c
	 *            native character,采用utf-16编码，而若是增补字符采用代码点表示
	 * @return ascii string
	 */
	private static String char2Ascii(char c) {
		if (c > 255) {// 非ASCII码包含的字符
			StringBuilder sb = new StringBuilder();
			sb.append(PREFIX);
			int code = (c >> 8);// 带符号右移8位，结果可定为正
			String tmp = Integer.toHexString(code);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			code = (c & 0xFF);// 和255(十进制)进行“与”运算
			tmp = Integer.toHexString(code);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			return sb.toString();
		} else {// ASCII码包含非打印控制字符、打印字符、扩展ASCII打印字符总计：255个
			return Character.toString(c);
		}
	}

	/**
	 * Ascii to native string. It's same as execut native2ascii.exe -reverse.
	 *
	 * @param str
	 *            ascii string
	 * @return native string
	 */
	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	/**
	 * Ascii to native character.
	 *
	 * @param str
	 *            ascii string
	 * @return native character
	 */
	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

	/**
	 *
	 * @param unicodes eg: \u4e86
	 * @return
	 */
	public static char deunicode(char[] unicodes) {
        int value = 0;

        char [] tmpChars = Arrays.copyOfRange(unicodes, 2, 6);

        for (char ch : tmpChars) {
            switch (ch) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                value = (value << 4) + (ch - '0');

                break;

            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
                value = (value << 4) + (ch - 'W');

                break;

            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
                value = (value << 4) + (ch - '7');

                break;
            }
        }

        return (char) value;
    }

    protected static String unicode(char c) {
        StringBuilder sb = new StringBuilder("\\u");

        int n = c;

        for (int i = 0; i < 4; ++i) {
            int digit = (n & 0xf000) >> 12;

        	sb.append(hex[digit]);
            n <<= 4;
        }

        return sb.toString();
    }

	public static void main(String[] args) {
		// 测试用例
		System.out.println(native2Ascii("了"));
		System.out.println(unicode('了'));
		System.out.println(deunicode(new char[]{'\\','u','4','e','8','6'}) );
//		System.out.println(ascii2Native(native2Ascii(native2Ascii("照a明了"))));
	}

}

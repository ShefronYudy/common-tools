package com.shefron.module.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.shefron.utils.CommonTool;

public class ObjectTool implements JsonSupport {
	public static Object parse(String jsonStr) {
		return parse(jsonStr, false);
	}

	public static Object parse(String jsonStr, boolean ignoreError) {
		return jsonStr == null ? null : parse(new IndexableString(jsonStr.trim()), ignoreError);
	}

	private static Object parse(IndexableString source, boolean ignoreError) {
		try {
			Object localObject1;
			if (source.currentChar() == '[') {
				return parseArray(source, ignoreError);
			}
			return parseObject(source, ignoreError);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid json string: " + source);
		} finally {
			source.clear();
		}
	}

	private static Object processToken(IndexableString source, boolean isTokenKey) {
		source.trimStart();
		if (source.moreChars()) {
			int index = 0;
			char startChar = source.currentChar();
			Object value = null;
			if ((startChar == '"') || (startChar == '\'')) {
				boolean checkNextQuot = true;
				for (;;) {
					index++;
					if (index >= source.length()) {
						break;
					}
					if (source.charEquals(index, startChar)) {
						if (checkNextQuot) {
							break;
						}
						checkNextQuot = true;
					} else if (source.charEquals(index, '\\')) {
						checkNextQuot = !checkNextQuot;
					} else if (!checkNextQuot) {
						checkNextQuot = true;
					}
				}
				value = recoverReservedChars(source.valueBy(1, index++));
			} else if (isTokenKey) {
				index = source.indexOf(":");
				value = source.valueBy(index).trim();
			} else {
				index = source.findNearestIndex(new char[] { ',', '}', ']' });
				value = detectValue(source.valueBy(index).trim());
			}
			source.moveIndex(index);
			return value;
		}
		return null;
	}

	private static int minNum(int... nums) {
		int res = Integer.MAX_VALUE;
		for (int num : nums) {
			res = (res < num) || (num == -1) ? res : num;
		}
		return res;
	}

	private static Object detectValue(String objStr) {
		if ("true".equals(objStr)) {
			return Boolean.valueOf(Boolean.parseBoolean(objStr));
		}
		if ("false".equals(objStr)) {
			return Boolean.valueOf(Boolean.parseBoolean(objStr));
		}
		if ("null".equals(objStr)) {
			return null;
		}
		if ("undefined".equals(objStr)) {
			return null;
		}
		if (objStr.indexOf('.') == -1) {
			long val = Long.valueOf(objStr).longValue();
			if ((val > 2147483647L) || (val < -2147483648L)) {
				return Long.valueOf(val);
			}
			return Integer.valueOf((int) val);
		}
		double val = Double.valueOf(objStr).doubleValue();
		if ((val > 3.4028234663852886E38D) || (val < 1.401298464324817E-45D)) {
			return Double.valueOf(val);
		}
		int idx = objStr.indexOf('.');
		if (idx != -1) {
			int endEIndex = objStr.toUpperCase().indexOf('E');
			if (endEIndex == -1) {
				endEIndex = objStr.length();
				if (endEIndex - idx > 7) {
					return Double.valueOf(val);
				}
			} else if (CommonTool.parseAsInt(objStr.substring(endEIndex + 1)) > 7) {
				return Double.valueOf(val);
			}
		}
		return Float.valueOf((float) val);
	}

	private static Object parseNextValue(IndexableString source, boolean ignoreError) {
		switch (source.currentChar()) {
		case '{':
			return parseObject(source, ignoreError);
		case '[':
			return parseArray(source, ignoreError);
		case ']':
		case '}':
			return null;
		}
		return processToken(source, false);
	}

	private static void parseNextValuePair(IndexableString source, Map<String, Object> oMap, boolean ignoreError) {
		String key = (String) processToken(source, true);
		source.trimStart();
		source.moveIndex();
		source.trimStart();

		oMap.put(key, parseNextValue(source, ignoreError));
	}

	private static Object parseObject(IndexableString source, boolean ignoreError) {
		return parseObject(source, null, ignoreError);
	}

	private static Object parseObject(IndexableString source, String mapClass, boolean ignoreError) {
		if ((extractNullValue(source)) || (source.length() == 0)) {
			return null;
		}
		Map oMap = null;
		if (mapClass != null) {
			try {
				oMap = (Map) Class.forName(mapClass).newInstance();
			} catch (ClassNotFoundException cnfe) {
				if (ignoreError) {
					oMap = new LinkedHashMap();
				} else {
					throw new RuntimeException(cnfe);
				}
			} catch (Exception e) {
				oMap = new LinkedHashMap();
			}
		} else if (oMap == null) {
			oMap = new LinkedHashMap();
		}
		source.moveIndex();
		source.trimStart();

		char ch = source.currentChar();
		while (ch != '}') {
			parseNextValuePair(source, oMap, ignoreError);
			source.trimStart();

			ch = source.currentChar();
			if (ch == ',') {
				source.moveIndex();
				source.trimStart();
			} else if (ch != '}') {
				throw new RuntimeException("invalid json string, expect comma, but get: " + source.currentChar());
			}
		}
		source.moveIndex();
		if (oMap.containsKey("__class")) {
			String className = (String) oMap.remove("__class");
			if (className != null) {
				try {
					Class<?> c = Class.forName(className);

					Object obj = c.newInstance();
					if (Date.class.isInstance(obj)) {
						Object timeVal = oMap.get("value");
						if (timeVal != null) {
							((Date) obj).setTime(((Long) timeVal).longValue());
						} else {
							obj = null;
						}
					} else {
						for (Object fieldName : oMap.keySet()) {
							try {
								Field f = c.getDeclaredField(String.valueOf(fieldName));
								f.setAccessible(true);
								f.set(obj, oMap.get(fieldName));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					oMap.clear();
					return obj;
				} catch (Exception e) {
					return oMap;
				}
			}
		}
		return oMap;
	}

	public static Object parseArray(IndexableString source, boolean ignoreError) {
		Collection<?> con = parseCollection(source, null, ignoreError);
		try {
			Collection<?> localCollection1 = con == null ? null : con;
			return localCollection1;
		} finally {
		}
	}

	public static Collection parseCollection(IndexableString source, String collectionClass, boolean ignoreError) {
		if ((extractNullValue(source)) || (source.length() == 0)) {
			return null;
		}
		Collection collection = null;
		if (collectionClass != null) {
			try {
				collection = (Collection) Class.forName(collectionClass).newInstance();
			} catch (ClassNotFoundException cnfe) {
				if (ignoreError) {
					collection = new ArrayList();
				} else {
					throw new RuntimeException(cnfe);
				}
			} catch (Exception e) {
				collection = new ArrayList();
			}
		} else {
			collection = new ArrayList();
		}
		source.moveIndex();
		source.trimStart();
		while (source.currentChar() != ']') {
			collection.add(parseNextValue(source, ignoreError));
			source.trimStart();

			char ch = source.currentChar();
			if (ch == ',') {
				source.moveIndex();
				source.trimStart();
			} else if (ch != ']') {
				throw new RuntimeException("invalid json string, expect comma, but get: " + source.currentChar());
			}
		}
		source.moveIndex();
		return collection;
	}

	private static boolean extractNullValue(IndexableString source) {
		if (source.startsWith("null")) {
			source.moveIndex(VALUE_NULL_LEN);
			return true;
		}
		return false;
	}

	public static String recoverReservedChars(String source) {
		if (source.contains("\\\\")) {
			source = source.replace("\\\\", "\\");
		}
		if (source.contains("\\\"")) {
			source = source.replace("\\\"", "\"");
		}
		if (source.indexOf("\\u") != -1) {
			StringBuilder sb = new StringBuilder();
			int len = source.length();
			int index = 0;

			int flag = 0;
			while (index < len) {
				char ch = source.charAt(index++);
				if (ch == '\\') {
					flag = 1;
				} else if ((flag == 1) && (Character.toLowerCase(ch) == 'u')) {
					if (len - index > 3) {
						for (int i = index; i < index + 4; i++) {
							if (!HEX_CHARS.contains(Character.valueOf(source.charAt(i)))) {
								sb.append("\\u");
								for (int j = index; j <= i; j++) {
									sb.append(source.charAt(j));
								}
								index = i + 1;
								flag = 0;

								break;
							}
						}
						if (flag != 0) {
							ch = (char) Integer.parseInt(source.substring(index, index + 4), 16);

							index += 4;
							flag = 0;
							sb.append(ch);
						}
					}
				} else {
					if (flag == 1) {
						flag = 0;
						sb.append('\\');
					}
					sb.append(String.valueOf(ch));
				}
			}
			source = sb.toString();
		}
		return source;
	}

	static final Set<Character> HEX_CHARS = new HashSet();

	static {
		HEX_CHARS.add(Character.valueOf('a'));
		HEX_CHARS.add(Character.valueOf('b'));
		HEX_CHARS.add(Character.valueOf('c'));
		HEX_CHARS.add(Character.valueOf('d'));
		HEX_CHARS.add(Character.valueOf('e'));
		HEX_CHARS.add(Character.valueOf('f'));
		HEX_CHARS.add(Character.valueOf('A'));
		HEX_CHARS.add(Character.valueOf('B'));
		HEX_CHARS.add(Character.valueOf('C'));
		HEX_CHARS.add(Character.valueOf('D'));
		HEX_CHARS.add(Character.valueOf('E'));
		HEX_CHARS.add(Character.valueOf('F'));
		HEX_CHARS.add(Character.valueOf('0'));
		HEX_CHARS.add(Character.valueOf('1'));
		HEX_CHARS.add(Character.valueOf('2'));
		HEX_CHARS.add(Character.valueOf('3'));
		HEX_CHARS.add(Character.valueOf('4'));
		HEX_CHARS.add(Character.valueOf('5'));
		HEX_CHARS.add(Character.valueOf('6'));
		HEX_CHARS.add(Character.valueOf('7'));
		HEX_CHARS.add(Character.valueOf('8'));
		HEX_CHARS.add(Character.valueOf('9'));
	}
}

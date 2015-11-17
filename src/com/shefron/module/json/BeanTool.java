package com.shefron.module.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.shefron.utils.CommonTool;

public class BeanTool implements JsonSupport {
	public static final Map<Class<?>, Class<?>> INTERFACE_MAPPING_CLASSES = new HashMap();
	private static final Map<Class<?>, Integer> CONSTANT_CLASS_INDEX_MAP = new HashMap();
	private static final int BOOLEAN_INDEX = 1;
	private static final int BYTE_INDEX = 2;
	private static final int CHAR_INDEX = 3;
	private static final int SHORT_INDEX = 4;
	private static final int INT_INDEX = 5;
	private static final int LONG_INDEX = 6;
	private static final int FLOAT_INDEX = 7;
	private static final int DOUBLE_INDEX = 8;
	private static final int NUMBER_INDEX = 9;
	private static final int STRING_INDEX = 10;
	private static final int DATE_INDEX = 11;
	private static final int OBJECT_INDEX = 12;

	static {
		CONSTANT_CLASS_INDEX_MAP.put(Boolean.class, Integer.valueOf(1));
		CONSTANT_CLASS_INDEX_MAP.put(Boolean.TYPE, Integer.valueOf(1));
		CONSTANT_CLASS_INDEX_MAP.put(Byte.class, Integer.valueOf(2));
		CONSTANT_CLASS_INDEX_MAP.put(Byte.TYPE, Integer.valueOf(2));
		CONSTANT_CLASS_INDEX_MAP.put(Character.class, Integer.valueOf(3));
		CONSTANT_CLASS_INDEX_MAP.put(Character.TYPE, Integer.valueOf(3));
		CONSTANT_CLASS_INDEX_MAP.put(Short.class, Integer.valueOf(4));
		CONSTANT_CLASS_INDEX_MAP.put(Short.TYPE, Integer.valueOf(4));
		CONSTANT_CLASS_INDEX_MAP.put(Integer.class, Integer.valueOf(5));
		CONSTANT_CLASS_INDEX_MAP.put(Integer.TYPE, Integer.valueOf(5));
		CONSTANT_CLASS_INDEX_MAP.put(Long.class, Integer.valueOf(6));
		CONSTANT_CLASS_INDEX_MAP.put(Long.TYPE, Integer.valueOf(6));
		CONSTANT_CLASS_INDEX_MAP.put(Float.class, Integer.valueOf(7));
		CONSTANT_CLASS_INDEX_MAP.put(Float.TYPE, Integer.valueOf(7));
		CONSTANT_CLASS_INDEX_MAP.put(Double.class, Integer.valueOf(8));
		CONSTANT_CLASS_INDEX_MAP.put(Double.TYPE, Integer.valueOf(8));
		CONSTANT_CLASS_INDEX_MAP.put(String.class, Integer.valueOf(10));
		CONSTANT_CLASS_INDEX_MAP.put(Date.class, Integer.valueOf(11));
		CONSTANT_CLASS_INDEX_MAP.put(Number.class, Integer.valueOf(9));
		CONSTANT_CLASS_INDEX_MAP.put(CharSequence.class, Integer.valueOf(10));
		CONSTANT_CLASS_INDEX_MAP.put(StringBuilder.class, Integer.valueOf(10));
		CONSTANT_CLASS_INDEX_MAP.put(StringBuffer.class, Integer.valueOf(10));
		CONSTANT_CLASS_INDEX_MAP.put(Object.class, Integer.valueOf(12));

		INTERFACE_MAPPING_CLASSES.put(Collection.class, ArrayList.class);
		INTERFACE_MAPPING_CLASSES.put(List.class, ArrayList.class);
		INTERFACE_MAPPING_CLASSES.put(Set.class, LinkedHashSet.class);
		INTERFACE_MAPPING_CLASSES.put(CharSequence.class, String.class);
		INTERFACE_MAPPING_CLASSES.put(AbstractSet.class, LinkedHashSet.class);

		INTERFACE_MAPPING_CLASSES.put(Map.class, LinkedHashMap.class);
		INTERFACE_MAPPING_CLASSES.put(AbstractMap.class, LinkedHashMap.class);
	}

	public static <T> T parse(String jsonStr, Class<T> clazz) {
		T t = parse(jsonStr, clazz, false);
		return t;
	}

	public static <T> T parse(String jsonStr, Class<T> clazz, boolean ignoreError) {
		if ((jsonStr == null) || (clazz == null)) {
			return null;
		}
		T t = parse(new IndexableString(jsonStr.trim()), clazz, ignoreError);
		return t;
	}

	public static <T> T parse(IndexableString source, Class<T> clazz, boolean ignoreError) {
		return (T) parse(source, clazz, String.class, Object.class, ignoreError);
	}

	public static Object parseValue(Class clazz, int classType, String token, boolean isKey, boolean ignoreError) {
		switch (classType) {
		case 1:
			return Boolean.valueOf(token);
		case 2:
			return Byte.valueOf(Byte.parseByte(token));
		case 3:
			return Character.valueOf(token.length() == 0 ? '\000' : token.charAt(0));
		case 4:
			return Short.valueOf(Short.parseShort(token));
		case 5:
			return Integer.valueOf(Integer.parseInt(token));
		case 6:
			return Long.valueOf(Long.parseLong(token));
		case 7:
			return Float.valueOf(Float.parseFloat(token));
		case 8:
			return Double.valueOf(Double.parseDouble(token));
		case 9:
			return detectNumberValue(token);
		case 10:
			if (INTERFACE_MAPPING_CLASSES.containsKey(clazz)) {
				clazz = (Class) INTERFACE_MAPPING_CLASSES.get(clazz);
			}
			try {
				return clazz.getConstructor(new Class[] { String.class })
						.newInstance(new Object[] { isKey ? token : token.substring(1, token.length() - 1) });
			} catch (Exception e) {
				if (ignoreError) {
					return null;
				}
				throw new RuntimeException("error while construct the string type value: " + token
						+ " with class type: " + clazz.getCanonicalName(), e);
			}
		case 11:
			long timestamp = CommonTool.parseAsLong(token);
			Object val = null;
			try {
				val = clazz.newInstance();
			} catch (Exception e) {
				if (ignoreError) {
					return null;
				}
				throw new RuntimeException("error while create date object instance: " + clazz.getCanonicalName(), e);
			}
			((Date) val).setTime(timestamp);

			return val;
		case 12:
			return detectValue(token);
		}
		throw new RuntimeException("Unhandled class type: " + classType);
	}

	public static <T extends Collection<K>, K> T parseCollection(String jsonStr, Class<T> clazz, Class<K> innerType,
			boolean ignoreError) {
		if ((jsonStr == null) || (clazz == null) || (innerType == null)) {
			return null;
		}
		T t = parseCollection(new IndexableString(jsonStr.trim()), clazz, innerType, ignoreError);
		return t;
	}

	public static <T extends Collection<K>, K> T parseCollection(IndexableString source, Class<T> clazz,
			Class<K> innerType, boolean ignoreError) {
		return (T) parse(source, clazz, null, innerType, ignoreError);
	}

	public static <T> T  parse(IndexableString source, Class clazz, Class innerTypeKey, Class innerTypeValue,
			boolean ignoreError) {
		if (clazz == null) {
			clazz = Object.class;
		}
		if (((clazz == Object.class) || (clazz.isArray())) && (source.charEquals(0, '['))) {
			source.moveIndex();
			source.trimStart();

			Class componentClass = clazz.getComponentType();
			boolean isArrayClass = clazz.isArray();

			List listVals = new ArrayList();

			boolean parseError = true;
			Object value = null;
			do {
				for (;;) {
					value = parse(source, isArrayClass ? componentClass : Object.class, ignoreError);
					if ((!isArrayClass) && (value != null) && (componentClass != Object.class)) {
						if (componentClass == null) {
							componentClass = value.getClass();
						} else if (componentClass != value.getClass()) {
							componentClass = Object.class;
						}
					}
					listVals.add(value);
					source.trimStart();
					if (!source.charEquals(0, ',')) {
						break;
					}
					source.moveIndex();
					source.trimStart();
				}
				if (source.charEquals(0, ']')) {
					parseError = false;
					break;
				}
				throw new RuntimeException("invalid array value in json string: " + source);
			} while (ignoreError);
			if (parseError) {
				skipUnHandledValue(source, '[', ']');
			}
			source.moveIndex();

			T arrVal = (T) Array.newInstance(componentClass, listVals.size());
			for (int i = 0; i < listVals.size(); i++) {
				Array.set(arrVal, i, listVals.get(i));
			}
			listVals.clear();
			listVals = null;

			return arrVal;
		}
		Integer classType = (Integer) CONSTANT_CLASS_INDEX_MAP.get(clazz);
		if (classType != null) {
			String token = processToken(source, false);
			if ((token == null) || ("null".equals(token))) {
				if (classType.intValue() < 10) {
					switch (classType.intValue()) {
					case 1:
						return (T) Boolean.FALSE;
					case 2:
						return (T) Byte.valueOf((byte) 0);
					case 3:
						return (T) Character.valueOf('\000');
					case 4:
						return (T) Short.valueOf((short) 0);
					case 5:
						return (T) Integer.valueOf(0);
					case 6:
						return (T) Long.valueOf(0L);
					case 7:
						return (T) Float.valueOf(0.0F);
					case 8:
						return (T) Double.valueOf(0.0D);
					}
				}
				return null;
			}
			return (T) parseValue(clazz, classType.intValue(), token, false, ignoreError);
		}
		if (extractNullValue(source)) {
			return null;
		}
		if ((Map.class.isAssignableFrom(clazz)) && (source.charEquals(0, '{'))) {
			Map returnVal = null;
			try {
				returnVal = (Map) (INTERFACE_MAPPING_CLASSES.containsKey(clazz)
						? ((Class) INTERFACE_MAPPING_CLASSES.get(clazz)).newInstance() : clazz.newInstance());
			} catch (Exception e) {
				if (!ignoreError) {
					throw new RuntimeException("can not create instance for class: " + clazz.getCanonicalName(), e);
				}
			}
			source.moveIndex();
			source.trimStart();
			boolean parseError = true;
			if (returnVal != null) {
				label760: do {
					for (;;) {
						Object key = processToken(source, true);
						classType = (Integer) CONSTANT_CLASS_INDEX_MAP.get(innerTypeKey);
						if (classType == null) {
							break label760;
						}
						key = parseValue(innerTypeKey, classType.intValue(), (String) key, true, ignoreError);

						source.trimStart();

						source.moveIndex();
						source.trimStart();

						Object value = parse(source, innerTypeValue, ignoreError);
						returnVal.put(key, value);

						source.trimStart();
						if (source.currentChar() != ',') {
							break;
						}
						source.moveIndex();
						source.trimStart();
					}
					if (source.currentChar() == '}') {
						parseError = false;
						break;
					}
				} while (ignoreError);
				throw new RuntimeException("invalid map value in json string: " + source);
			}
			if (parseError) {
				skipUnHandledValue(source, '{', '}');
			}
			source.moveIndex();

			return (T) returnVal;
		}
		if ((Collection.class.isAssignableFrom(clazz)) && (source.charEquals(0, '['))) {
			Collection returnVal = null;
			try {
				returnVal = (Collection) (INTERFACE_MAPPING_CLASSES.containsKey(clazz)
						? ((Class) INTERFACE_MAPPING_CLASSES.get(clazz)).newInstance() : clazz.newInstance());
			} catch (Exception e) {
				if (!ignoreError) {
					throw new RuntimeException("can not create instance for class: " + clazz.getCanonicalName(), e);
				}
			}
			source.moveIndex();
			source.trimStart();
			boolean parseError = true;
			if (returnVal != null) {
				Class componentClass = innerTypeValue;
				do {
					for (;;) {
						returnVal.add(parse(source, componentClass, ignoreError));
						source.trimStart();
						if (!source.charEquals(0, ',')) {
							break;
						}
						source.moveIndex();
						source.trimStart();
					}
					if (source.charEquals(0, ']')) {
						parseError = false;
						break;
					}
				} while (ignoreError);
				throw new RuntimeException("invalid array value in json string: " + source);
			}
			if (parseError) {
				skipUnHandledValue(source, '[', ']');
			}
			source.moveIndex();

			return (T) returnVal;
		}
		T returnVal = null;
		boolean parseError = true;
		try {
			returnVal = (T) clazz.newInstance();
		} catch (Exception e) {
			if (!ignoreError) {
				throw new RuntimeException("Error while create instance of object: " + clazz.getCanonicalName(), e);
			}
			return null;
		}
		source.moveIndex();
		source.trimStart();
		if (returnVal != null) {
			Object value = null;
			do {
				for (;;) {
					String key = processToken(source, true);
					value = null;

					source.trimStart();

					source.moveIndex();
					source.trimStart();
					Field field;
					try {
						field = clazz.getDeclaredField(key);
					} catch (Exception e) {
						field = null;
					}
					if ((field != null) && (!Modifier.isFinal(field.getModifiers()))) {
						Class fieldType = field.getType();
						if (Map.class.isAssignableFrom(fieldType)) {
							Type genericType = field.getGenericType();
							boolean flag = true;
							if ((genericType instanceof ParameterizedType)) {
								ParameterizedType parameterizedType = (ParameterizedType) genericType;
								Type[] types = parameterizedType.getActualTypeArguments();
								if (types.length == 2) {
									value = parse(source, fieldType, (Class) types[0], (Class) types[1], ignoreError);

									flag = false;
								}
							}
							if (flag) {
								value = parse(source, fieldType, ignoreError);
							}
						} else if (Collection.class.isAssignableFrom(fieldType)) {
							Type genericType = field.getGenericType();
							boolean flag = true;
							if ((genericType instanceof ParameterizedType)) {
								ParameterizedType parameterizedType = (ParameterizedType) genericType;
								Type[] types = parameterizedType.getActualTypeArguments();
								if (types.length == 1) {
									value = parse(source, fieldType, null, (Class) types[0], ignoreError);

									flag = false;
								}
							}
							if (flag) {
								value = parse(source, fieldType, ignoreError);
							}
						} else {
							value = parse(source, fieldType, ignoreError);
						}
						if (value != null) {
							field.setAccessible(true);
							try {
								field.set(returnVal, value);
							} catch (Exception e) {
								if (!ignoreError) {
									throw new RuntimeException(
											"can not set the value: " + value + " for field: " + field.getName(), e);
								}
							}
						}
					} else if (key.length() > 0) {
						String setMethodName = toSetMethodName(key);

						Method[] methods = clazz.getDeclaredMethods();
						Method method = null;
						for (Method m : methods) {
							if ((m.getName().equals(setMethodName)) && (m.getParameterTypes().length == 1)
									&& (Modifier.isPublic(m.getModifiers()))) {
								method = m;
								break;
							}
						}
						if (method == null) {
							methods = clazz.getMethods();
							for (Method m : methods) {
								if ((m.getName().equals(setMethodName)) && (m.getParameterTypes().length == 1)
										&& (Modifier.isPublic(m.getModifiers()))) {
									method = m;
									break;
								}
							}
						}
						if (method != null) {
							Class fieldType = method.getParameterTypes()[0];
							if (Map.class.isAssignableFrom(fieldType)) {
								Type genericType = method.getGenericParameterTypes()[0];
								boolean flag = true;
								if ((genericType instanceof ParameterizedType)) {
									ParameterizedType parameterizedType = (ParameterizedType) genericType;
									Type[] types = parameterizedType.getActualTypeArguments();
									if (types.length == 2) {
										value = parse(source, fieldType, (Class) types[0], (Class) types[1],
												ignoreError);

										flag = false;
									}
								}
								if (flag) {
									value = parse(source, fieldType, ignoreError);
								}
							} else if (Collection.class.isAssignableFrom(fieldType)) {
								Type genericType = method.getGenericParameterTypes()[0];
								boolean flag = true;
								if ((genericType instanceof ParameterizedType)) {
									ParameterizedType parameterizedType = (ParameterizedType) genericType;
									Type[] types = parameterizedType.getActualTypeArguments();
									if (types.length == 1) {
										value = parse(source, fieldType, null, (Class) types[0], ignoreError);

										flag = false;
									}
								}
								if (flag) {
									value = parse(source, fieldType, ignoreError);
								}
							} else {
								value = parse(source, fieldType, ignoreError);
							}
							if (value != null) {
								method.setAccessible(true);
								try {
									method.invoke(returnVal, new Object[] { value });
								} catch (Exception e) {
									if (!ignoreError) {
										throw new RuntimeException(
												"can not set the value: " + value + " for method: " + method.getName()
														+ " with parameter type: " + fieldType.getCanonicalName(),
												e);
									}
								}
							}
						}
					}
					source.trimStart();
					if (source.currentChar() != ',') {
						break;
					}
					source.moveIndex();
				}
				if (source.currentChar() == '}') {
					break;
				}
				throw new RuntimeException("invalid object value in json string: " + source);
			} while (ignoreError);
			if (source.currentChar() == '}') {
				parseError = false;
			}
		}
		if (parseError) {
			skipUnHandledValue(source, '{', '}');
		}
		source.moveIndex();

		return returnVal;
	}

	private static boolean extractNullValue(IndexableString source) {
		if (source.startsWith("null")) {
			source.moveIndex(VALUE_NULL_LEN);
			return true;
		}
		return false;
	}

	private static void skipUnHandledValue(IndexableString source, char endChar, char startChar) {
		if (!source.charEquals(0, ']')) {
			int index = 0;

			int checkNextEnd = 0;
			boolean checkChar = true;
			boolean checkNextQuot = true;
			
			label151: while (index < source.length()) {
				char ch = source.charAt(index++);
				switch (ch) {
				case '\\':
					checkNextQuot = !checkNextQuot;
					break;
				case '"':
					if (checkNextQuot) {
						checkChar = !checkChar;
					}
					checkNextQuot = true;
					break;
				default:
					if (checkChar) {
						if (ch == startChar) {
							if (checkChar) {
								checkNextEnd++;
							}
						} else if (ch == endChar) {
							if (checkNextEnd == 0) {
								break label151;
							}
							checkNextEnd--;
						}
					}
					checkNextQuot = true;
				}
			}
			source.moveIndex(index);
		}
	}

	private static String processToken(IndexableString source, boolean isTokenKey) {
		source.trimStart();
		if (source.moreChars()) {
			int index = 0;
			char startChar = source.currentChar();
			String value = null;
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
					}
				}
				value = isTokenKey ? ObjectTool.recoverReservedChars(source.valueBy(1, index++))
						: ObjectTool.recoverReservedChars(source.valueBy(0, ++index));
			} else if (isTokenKey) {
				index = source.indexOf(":");
				value = source.valueBy(index).trim();
			} else {
				index = source.findNearestIndex(new char[] { ',', '}', ']' });
				value = source.valueBy(index).trim();
			}
			source.moveIndex(index);
			return value;
		}
		return null;
	}

	private static Object detectNumberValue(String objStr) {
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

	private static Object detectValue(String objStr) {
		if ("true".equals(objStr)) {
			return Boolean.valueOf(Boolean.parseBoolean(objStr));
		}
		if (((objStr.length() > 1) && (objStr.charAt(0) == '"') && (objStr.charAt(objStr.length() - 1) == '"'))
				|| ((objStr.charAt(0) == '\'') && (objStr.charAt(objStr.length() - 1) == '\''))) {
			return objStr.substring(1, objStr.length() - 1);
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
			long val;
			try {
				val = Long.valueOf(objStr).longValue();
			} catch (NumberFormatException nfe) {
				return objStr;
			}
			if ((val > 2147483647L) || (val < -2147483648L)) {
				return Long.valueOf(val);
			}
			return Integer.valueOf((int) val);
		}
		double val;
		try {
			val = Double.valueOf(objStr).doubleValue();
		} catch (NumberFormatException nfe) {
			return objStr;
		}
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

	private static String toSetMethodName(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static void main(String[] args) throws Exception {
		Bean[] x = (Bean[]) parse(
				"[{\"i\":1,\"b\":true,\"s\":\"test\",\"f\":1.01,\"x\":" + System.currentTimeMillis()
						+ "},{\"i\":1,\"b\":true,\"s\":\"test\",\"f\":1.01,\"x\":" + System.currentTimeMillis() + "}]",
				Bean[].class, false);

		System.out.println(Arrays.toString(x));
	}
}

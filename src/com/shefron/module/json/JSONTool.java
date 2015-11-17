package com.shefron.module.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.shefron.utils.CommonTool;

public class JSONTool implements JsonSupport {
	public static final boolean CLEAN_JSON_STYLE = true;

	private static void convertMapToJson(Map<Object, Object> map, StringBuilder sb) {
		sb.append('{');
		if (!map.isEmpty()) {
			for (Object key : map.keySet()) {
				convertObjectToJson(String.valueOf(key), map.get(key), sb);
				sb.append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append('}');
	}

	private static void convertArrayToJson(Object arr, StringBuilder sb) {
		sb.append('[');

		int len = Array.getLength(arr);
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				Object val = Array.get(arr, i);
				convertObjectToJson(val, sb);
				sb.append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(']');
	}

	private static void insertClassDesc(StringBuilder sb, int offset, String key, String value) {
		StringBuilder mClassDesc = new StringBuilder();

		mClassDesc.append('"');
		mClassDesc.append(key);
		mClassDesc.append('"');

		mClassDesc.append(':');

		mClassDesc.append('"');
		mClassDesc.append(value);
		mClassDesc.append('"');

		mClassDesc.append(',');

		sb.insert(offset, mClassDesc);

		mClassDesc.setLength(0);
	}

	private static void convertCollectionToJson(Collection<?> mapVal, StringBuilder sb) {
		sb.append('[');
		if (!mapVal.isEmpty()) {
			for (Object val : mapVal) {
				convertObjectToJson(val, sb);
				sb.append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(']');
	}

	private static void convertNumberToJson(Number obj, StringBuilder sb) {
		sb.append(obj);
	}

	private static void convertBeanToJson(Object obj, StringBuilder sb) {
		if ((obj instanceof Date)) {
			sb.append(((Date) obj).getTime());
		} else {
			sb.append('{');
			Class<?> c = obj.getClass();

			Set<String> fieldNameSet = new HashSet();
			Field[] fields = c.getDeclaredFields();
			for (Field f : fields) {
				int modifiers = f.getModifiers();
				String fieldName = f.getName();
				if ((!f.isEnumConstant()) && (!Modifier.isFinal(modifiers)) && (!Modifier.isTransient(modifiers))) {
					f.setAccessible(true);
					try {
						convertObjectToJson(fieldName, f.get(obj), sb);
						sb.append(',');
						fieldNameSet.add(fieldName);
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					}
				}
			}
			Method[] methods = c.getDeclaredMethods();
			for (Method m : methods) {
				String methodName = m.getName();
				int modifiers = m.getModifiers();
				if ((Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) {
					String fieldName;
					if ((methodName.startsWith("is"))
							&& ((m.getReturnType() == Boolean.class) || (m.getReturnType() == Boolean.TYPE))) {
						fieldName = Character.toLowerCase(methodName.charAt(IS_PREFIX_LEN))
								+ methodName.substring(IS_PREFIX_LEN_1);
					} else {
						if (!methodName.startsWith("is")) {
							continue;
						}
						fieldName = Character.toLowerCase(methodName.charAt(GET_PREFIX_LEN))
								+ methodName.substring(GET_PREFIX_LEN_1);
					}
					if ((!fieldNameSet.contains(fieldName)) && (fieldName.length() != 0)) {
						m.setAccessible(true);
						try {
							convertObjectToJson(fieldName, m.invoke(obj, EMTPY_OBJ_ARR), sb);
							sb.append(',');
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {
						}
					}
				}
			}
			fieldNameSet.clear();
			fieldNameSet = null;
			if (sb.charAt(sb.length() - 1) == ',') {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append('}');
		}
	}

	private static void convertObjectToJson(Object obj, StringBuilder sb) {
		convertObjectToJson(obj, sb, null);
	}

	private static void convertObjectToJson(Object obj, StringBuilder sb, String key) {
		if ((obj instanceof Number)) {
			convertNumberToJson((Number) obj, sb);
		} else if ((obj instanceof Character)) {
			sb.append(obj);
		} else if ((obj instanceof Boolean)) {
			sb.append(obj);
		} else if ((obj instanceof CharSequence)) {
			sb.append('"');
			sb.append(replaceReservedChars(obj.toString()));
			sb.append('"');
		} else if ((obj instanceof Map)) {
			Map<Object, Object> mapVal = (Map) CommonTool.uncheckedMapCast(obj);
			convertMapToJson(mapVal, sb);
		} else if ((obj instanceof Collection)) {
			Collection<?> mapVal = (Collection) CommonTool.uncheckedCollectionCast(obj);
			convertCollectionToJson(mapVal, sb);
		} else if (obj == null) {
			sb.append("null");
		} else if (obj.getClass().isArray()) {
			convertArrayToJson(obj, sb);
		} else {
			convertBeanToJson(obj, sb);
		}
	}

	private static void convertObjectToJson(String key, Object obj, StringBuilder sb) {
		key = replaceReservedChars(key);

		sb.append('"');
		sb.append(key);
		sb.append('"');
		sb.append(':');

		convertObjectToJson(obj, sb, key);
	}

	public static String replaceReservedChars(String source) {
		if (source.contains("\\")) {
			source = source.replace("\\", "\\\\");
		}
		if (source.contains("\"")) {
			source = source.replace("\"", "\\\"");
		}
		return source;
	}

	public static String convertObjectToJson(Object o) {
		StringBuilder sb = new StringBuilder();
		convertObjectToJson(o, sb);
		return sb.toString();
	}

	public static Object convertJsonToObject(String jsonStr) {
		return convertJsonToObject(jsonStr, false);
	}

	public static Object convertJsonToObject(String jsonStr, boolean ignoreError) {
		return jsonStr != null ? ObjectTool.parse(jsonStr, ignoreError) : null;
	}

	public static <T> T convertJsonToObject(String jsonStr, Class<T> clazz, boolean ignoreError) {
		return (T) (jsonStr != null ? BeanTool.parse(jsonStr, clazz, ignoreError) : null);
	}

	public static <T extends Collection<K>, K> T convertJsonToArray(String jsonStr, Class<T> clazz, Class<K> innerType,
			boolean ignoreError) {
		return jsonStr != null ? BeanTool.parseCollection(jsonStr, clazz, innerType, ignoreError) : null;
	}

	public static void main(String[] args) {
		String data = "{}";

		Object json = convertJsonToObject(data);

		System.out.println(json);
	}
}

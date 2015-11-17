package org.shefron.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonTool {
	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if ((o instanceof String)) {
			return "".equals(o);
		}
		if ((o instanceof Collection)) {
			return ((Collection) o).size() == 0;
		}
		if ((o instanceof Map)) {
			return ((Map) o).size() == 0;
		}
		if ((o instanceof Object[])) {
			return ((Object[]) o).length == 0;
		}
		return false;
	}

	public static <T> T uncheckedCast(Object obj) {
		return (T) obj;
	}

	public static <T> T uncheckedMapCast(Object obj) {
		if ((obj instanceof Map)) {
			return (T) obj;
		}
		return null;
	}

	public static <T> T uncheckedCollectionCast(Object obj) {
		if ((obj instanceof Collection)) {
			return (T) obj;
		}
		return null;
	}

	public static <T> T uncheckedSetCast(Object obj) {
		if ((obj instanceof Set)) {
			return (T) obj;
		}
		return null;
	}

	public static <T> T uncheckedListCast(Object obj) {
		if ((obj instanceof List)) {
			return (T) obj;
		}
		return null;
	}

	public static <T> T castAs(Object obj, Class<T> c) {
		return (T) castAs(obj, c, null);
	}

	public static <T> T castAs(Object obj, Class<T> c, Object defaultValue) {
		if (c.isInstance(obj)) {
			return (T) c.cast(obj);
		}
		return (T) c.cast(defaultValue);
	}

	public static String castAsString(Object obj) {
		return (String) castAs(obj, String.class);
	}

	public static boolean castAsBoolean(Object obj) {
		return ((Boolean) castAs(obj, Boolean.class, Boolean.valueOf(false))).booleanValue();
	}

	public static Integer castAsInteger(Object obj) {
		return (Integer) castAs(obj, Integer.class, Integer.valueOf(0));
	}

	public static <T> T forceCast(Object obj, Class<T> c) {
		if (c.isInstance(obj)) {
			return (T) c.cast(obj);
		}
		if (obj == null) {
			return null;
		}
		throw new RuntimeException("Object: " + obj + " does not match the type: " + c.getCanonicalName());
	}

	public static Serializable forceCastToSerializable(Object obj) {
		return (Serializable) forceCast(obj, Serializable.class);
	}

	public static int parseAsInt(Object obj) {
		return parseAsInt(obj, 0);
	}

	public static int parseAsInt(Object obj, int defaultValue) {
		int val;
		if ((obj instanceof String)) {
			try {
				val = Integer.parseInt((String) obj);
			} catch (Exception e) {
				val = defaultValue;
			}
		} else {
			if ((obj instanceof Number)) {
				val = ((Number) obj).intValue();
			} else {
				val = defaultValue;
			}
		}
		return val;
	}

	public static long parseAsLong(Object obj) {
		return parseAsLong(obj, 0L);
	}

	public static long parseAsLong(Object obj, long defaultValue) {
		long val;
		if ((obj instanceof String)) {
			try {
				val = Long.parseLong((String) obj);
			} catch (Exception e) {
				val = defaultValue;
			}
		} else {
			if ((obj instanceof Number)) {
				val = ((Number) obj).longValue();
			} else {
				val = defaultValue;
			}
		}
		return val;
	}

	public static float parseAsFloat(Object obj) {
		return parseAsFloat(obj, 0.0F);
	}

	public static float parseAsFloat(Object obj, float defaultValue) {
		float val;
		if ((obj instanceof String)) {
			try {
				val = Float.parseFloat((String) obj);
			} catch (Exception e) {
				val = defaultValue;
			}
		} else {
			if ((obj instanceof Number)) {
				val = ((Number) obj).floatValue();
			} else {
				val = defaultValue;
			}
		}
		return val;
	}

	public static double parseAsDouble(Object obj) {
		return parseAsDouble(obj, 0.0D);
	}

	public static double parseAsDouble(Object obj, double defaultValue) {
		double val;
		if ((obj instanceof String)) {
			try {
				val = Double.parseDouble((String) obj);
			} catch (Exception e) {
				val = defaultValue;
			}
		} else {
			if ((obj instanceof Number)) {
				val = ((Number) obj).doubleValue();
			} else {
				val = defaultValue;
			}
		}
		return val;
	}

	public static boolean parseAsBoolean(Object obj) {
		return parseAsBoolean(obj, false);
	}

	public static boolean parseAsBoolean(Object obj, boolean defaultValue) {
		boolean val;
		if ((obj instanceof String)) {
			try {
				val = Boolean.valueOf((String) obj).booleanValue();
			} catch (Exception e) {
				val = defaultValue;
			}
		} else {
			if ((obj instanceof Boolean)) {
				val = ((Boolean) obj).booleanValue();
			} else {
				val = defaultValue;
			}
		}
		return val;
	}

	public static void deepClear(Object obj) {
		if ((obj instanceof Map)) {
			Map mapObj = (Map) obj;
			for (Object key : mapObj.keySet()) {
				deepClear(key);
				deepClear(mapObj.get(key));
			}
			try {
				mapObj.clear();
			} catch (Exception e) {
			}
			mapObj = null;
		} else if ((obj instanceof Collection)) {
			Collection colObj = (Collection) obj;
			for (Object val : colObj) {
				deepClear(val);
			}
			try {
				colObj.clear();
			} catch (Exception e) {
			}
			colObj = null;
		} else if ((obj instanceof Object[])) {
			Object[] arr = (Object[]) obj;
			for (Object val : arr) {
				deepClear(val);
			}
			arr = null;
		}
	}

	public static synchronized long generateId() {
		return System.currentTimeMillis() + idCounter++;
	}

	public static void listInt2Long(List numberList) {
		if (numberList != null) {
			for (int i = 0; i < numberList.size(); i++) {
				numberList.set(i, Long.valueOf(parseAsLong(numberList.get(i))));
			}
		}
	}

	public static void listFloat2Double(List numberList) {
		if (numberList != null) {
			for (int i = 0; i < numberList.size(); i++) {
				numberList.set(i, Double.valueOf(parseAsDouble(numberList.get(i))));
			}
		}
	}

	public static final Map<String, String> TRANSFER_CHAR_MAP = new HashMap();

	static {
		TRANSFER_CHAR_MAP.put("\\r", "\r");
		TRANSFER_CHAR_MAP.put("\\n", "\n");
		TRANSFER_CHAR_MAP.put("\\t", "\t");
		TRANSFER_CHAR_MAP.put("\\b", "\b");
		TRANSFER_CHAR_MAP.put("\\f", "\f");
	}

	public static final Pattern regex = Pattern.compile("\\\\(r|n|t|b|f)");

	public static String replaceTransferChar(String val) {
		if (val != null) {
			Matcher matcher = regex.matcher(val);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, (String) TRANSFER_CHAR_MAP.get(matcher.group()));
			}
			matcher.appendTail(sb);
			val = sb.toString();

			sb.setLength(0);
		}
		return val;
	}

	public static long idCounter = 0L;
}

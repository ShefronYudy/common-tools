package org.shefron.fc.web;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonBuilder {
	static Set<Class<?>> simpleTypes = new HashSet<Class<?>>();
	static Set<Class<?>> ignoredTypes = new HashSet<Class<?>>();
	static {
		simpleTypes.add(Byte.TYPE);
		simpleTypes.add(Short.TYPE);
		simpleTypes.add(Integer.TYPE);
		simpleTypes.add(Long.TYPE);
		simpleTypes.add(Float.TYPE);
		simpleTypes.add(Double.TYPE);
		simpleTypes.add(Boolean.TYPE);
		ignoredTypes.add(Class.class);
	}

	private Set<Class<?>> excludeClasses;

	public JsonBuilder(Class<?>... userClassesToExclude) {
		this.excludeClasses = new HashSet<Class<?>>();

		for (Class<?> type : userClassesToExclude) {
			this.excludeClasses.add(type);
		}
		this.excludeClasses.addAll(ignoredTypes);
	}

	public String build(Object o) {
		try {
			return buildNode("actionbean", o, new StringBuilder(), false);
		} catch (Exception e) {
		}
		return "";
	}

	public boolean isExcludedType(Class<?> type) {
		for (Class<?> excludedType : this.excludeClasses) {
			if (excludedType.isAssignableFrom(type)) {
				return true;
			} else if (type.isArray()
					&& excludedType.isAssignableFrom(type.getComponentType())) {
				return true;
			}
		}
		return false;
	}

	public boolean isScalarType(Object in) {
		if (in == null)
			return true;
		Class type = in.getClass();
		return simpleTypes.contains(type)
				|| Number.class.isAssignableFrom(type)
				|| String.class.isAssignableFrom(type)
				|| Boolean.class.isAssignableFrom(type)
				|| Date.class.isAssignableFrom(type);
	}

	public String getScalarAsString(Object in) {
		if (in == null)
			return "null";
		Class type = in.getClass();
		if (String.class.isAssignableFrom(type)) {
			return quote((String) in);
		} else if (Date.class.isAssignableFrom(type)) {
			return "new Date(" + ((Date) in).getTime() + ")";
		} else {
			return in.toString();
		}
	}

	public static String quote(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}

		char c = 0;
		int len = string.length();
		StringBuilder sb = new StringBuilder(len + 10);

		sb.append('"');
		for (int i = 0; i < len; ++i) {
			c = string.charAt(i);
			switch (c) {
			case '\\':
			case '"':
				sb.append('\\').append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				if (c < ' ') {
					// The following takes lower order chars and creates
					// unicode style
					// char literals for them (e.g. \u00F3)
					sb.append("\\u");
					String hex = Integer.toHexString(c);
					int pad = 4 - hex.length();
					for (int j = 0; j < pad; ++j) {
						sb.append("0");
					}
					sb.append(hex);
				} else {
					sb.append(c);
				}
			}
		}

		sb.append('"');
		return sb.toString();
	}

	public String buildNode(String targetName, Object in, StringBuilder out,
			boolean incollect) throws Exception {
		if (incollect) {
			out.append(targetName);
			out.append(":");
		}
		if (Collection.class.isAssignableFrom(in.getClass())) {
			out.append("[");
			int length = ((Collection) in).size(), i = 0;
			for (Object value : (Collection) in) {
				if (isScalarType(value)) {
					out.append(getScalarAsString(value));
				} else {
					buildNode(targetName, value, out, false);
				}
				if (i++ != length - 1) {
					out.append(", ");
				}
			}
			out.append("]");

		} else if (in.getClass().isArray()) {
			out.append("[");
			int length = Array.getLength(in);
			for (int i = 0; i < length; i++) {
				Object value = Array.get(in, i);
				if (isScalarType(value)) {
					out.append(getScalarAsString(value));
				} else {
					buildNode(targetName, value, out, false);
				}
				if (i != length - 1) {
					out.append(", ");
				}
			}
			out.append("]");

		} else if (Map.class.isAssignableFrom(in.getClass())) {
			out.append("{");
			int ad = out.length();
			for (Map.Entry<?, ?> entry : ((Map<?, ?>) in).entrySet()) {
				String propertyName = getScalarAsString(entry.getKey());
				Object value = entry.getValue();

				if (isScalarType(value)) {
					if (out.length() > ad) {
						out.append(", ");
					}
					out.append(propertyName);
					out.append(":");
					out.append(getScalarAsString(value));
				} else {
					if (out.length() > ad) {
						out.append(", ");
					}
					buildNode(propertyName, value, out, true);
				}
			}
			out.append("}");
		} else {
			out.append("{");
			int ad = out.length();
			PropertyDescriptor[] props = Introspector
					.getBeanInfo(in.getClass()).getPropertyDescriptors();

			for (PropertyDescriptor property : props) {
				try {
					Method readMethod = property.getReadMethod();
					if (readMethod != null) {
						Object value = property.getReadMethod().invoke(in);

						if (isExcludedType(property.getPropertyType())
								|| value == null) {
							continue;
						}

						if (isScalarType(value)) {

							if (out.length() > ad) {
								out.append(", ");
							}
							out.append(property.getName());
							out.append(":");
							out.append(getScalarAsString(value));
						} else {
							if (out.length() > ad) {
								out.append(", ");
							}
							buildNode(property.getName(), value, out, true);
						}
					}
				} catch (Exception e) {

				}
			}
			out.append("}");
		}
		// this.objectValues.put(targetName, out.toString());
		return out.toString();
	}

	public static String formatJson(String json, String fillStringUnit) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}

		int fixedLenth = 0;
		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			// Ô¤¶ÁÈ¡
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}
		}

		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < tokenList.size() - 1
					&& tokenList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}

		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < tokenList.size(); i++) {

			String token = tokenList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count, fillStringUnit);
				continue;
			}
			if (token.equals(":")) {
				buf.append(" ").append(token).append(" ");
				continue;
			}
			if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("}")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("]")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}

			buf.append(token);
			// ×ó¶ÔÆë
			if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
				int fillLength = fixedLenth - token.getBytes().length;
				if (fillLength > 0) {
					for (int j = 0; j < fillLength; j++) {
						buf.append(" ");
					}
				}
			}
		}
		return buf.toString();
	}

	private static String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);

			if (!isInYinHao
					&& (token.equals(":") || token.equals("{")
							|| token.equals("}") || token.equals("[")
							|| token.equals("]") || token.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(token);
				}

				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(token);
		}
		return buf.toString();
	}

	private static void doFill(StringBuilder buf, int count,
			String fillStringUnit) {
		buf.append("\n");
		for (int i = 0; i < count; i++) {
			buf.append(fillStringUnit);
		}
	}

}

package org.shefron.fc.resourcebundle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OrderProperties {

	private static OrderProperties instance = null;
	/** 按行解析properties文件 */
	private List<String> lines = new ArrayList<String>();
	/** key=value */
	private Properties properties = new Properties();
	private String resource;
	private String encoding;

	private OrderProperties() {
	}

	public synchronized static OrderProperties instance() {
		if (instance == null) {
			instance = new OrderProperties();
		}
		return instance;
	}

	public synchronized String getProperty(String key)
			throws UnsupportedEncodingException {
		return properties.getProperty(key);
	}

	public synchronized String setProperty(String key, String value) {
		return (String) properties.setProperty(key, value);
	}

	public synchronized void load(String filepath) throws IOException {
		load(filepath, null);
	}

	public synchronized void load(String filepath, String encoding)
			throws IOException {
		clear();
		if (filepath == null) {
			throw new IOException("File must not be null");
		}
		this.resource = filepath;

		FileInputStream inputStream = new FileInputStream(filepath);
		InputStreamReader reader = null;
		if (encoding == null) {
			reader = new InputStreamReader(inputStream);
		} else {
			this.encoding = encoding;
			reader = new InputStreamReader(inputStream, encoding);
		}
		BufferedReader bufferReader = new BufferedReader(reader);
		String line = null;
		while ((line = bufferReader.readLine()) != null) {
			String lineStr = line.trim();

			int index = -1;
			// properties comments '#' or '!'
			if (lineStr.startsWith("#") || lineStr.startsWith("!")) {
				// 暂不做处理
				lines.add(line);

				// key and value '=' or ':'
			} else if ((index = lineStr.indexOf('=')) != -1
					|| (index = lineStr.indexOf(':')) != -1) {
				// add key
				String key = lineStr.substring(0, index);
				String value = lineStr.substring(index + 1);
				lines.add(key);
				properties.put(key, value);
				// blank
			} else {
				lines.add(line);
			}
		}

		if (bufferReader != null) {
			bufferReader.close();
		}
		if (reader != null) {
			reader.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

	}

	public synchronized void saveProperties() throws IOException {
		if (this.resource == null) {
			throw new IOException("File must not be null");
		}

		FileOutputStream outStream = new FileOutputStream(this.resource);
		OutputStreamWriter writer = null;
		if (this.encoding == null) {
			writer = new OutputStreamWriter(outStream);
		} else {
			writer = new OutputStreamWriter(outStream, encoding);
		}

		for (String line : this.lines) {
			String str = properties.getProperty(line);
			writer.write((str == null ? line : (line + "=" + str)) + "\n");
		}

		if (writer != null) {
			writer.close();
		}

		if (outStream != null) {
			outStream.close();
		}

	}

	private void clear() {
		try {
			lines.clear();
			properties.clear();
		} catch (Exception e) {

		}
	}

}

package org.shefron.fc.web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 处理HTTP请求并响应
 * 
 * @author Administrator
 * 
 */
public class BusiHandler implements HttpHandler {

	/**
	 * 处理HTTP请求并响应
	 */
	public void handle(HttpExchange exchange) {
		// if the url "http://x.x.x.x:port/http/req.xml",then the uri is
		// "/http/req.xml"
		URI reqURI = exchange.getRequestURI();
		System.out.println("RequestURI=" + reqURI.toString());

		String method = exchange.getRequestMethod();
		System.out.println("method:" + method);

		String protocol = exchange.getProtocol();
		System.out.println("protocol:" + protocol);

		Headers reqHeaders = exchange.getRequestHeaders();
		System.out.println("requestHeaders:");
		String host = "";
		String reqXmlHead = null;
		for (Entry<String, List<String>> entry : reqHeaders.entrySet()) {
			System.out.println("key=" + entry.getKey() + ",value="
					+ entry.getValue());
			if ("Host".equalsIgnoreCase(entry.getKey())) {
				host = entry.getValue().get(0);
			}
			if ("xmlHead".equalsIgnoreCase(entry.getKey())) {
				reqXmlHead = entry.getValue().get(0);
			}
		}

		if ("HTTP/1.1".equals(protocol)) {
			protocol = "http";
		}

		System.out.println("------------------------------------");
		System.out.println("URL=" + protocol + "://" + host + reqURI);
		System.out.println("xmlhead=" + reqXmlHead);

		// 处理请求体
		// handleRequestBody(exchange.getRequestBody());
		justPrint(exchange.getRequestBody());

		try {

			Headers responseHeaders = exchange.getResponseHeaders();
			responseHeaders
					.set("content-type", "multipart/mixed;charset=utf-8");

			StringBuffer xmlHead = new StringBuffer();
			xmlHead.append("<resinfo>");
			xmlHead.append("<state>true</state>");
			xmlHead.append("</resinfo>");
			String headStr = "<head><content><![CDATA[" + xmlHead.toString()
					+ "]]></content></head>";

			responseHeaders.set("xmlhead", headStr);

			OutputStream ut = exchange.getResponseBody();
			OutputStreamWriter writer = new OutputStreamWriter(ut, "UTF-8");

			String xmlStr = "<resinfo><status>正确</status></resinfo>";
			String xmlBody = "xmlbody=<InterBoss><SvcCont><![CDATA[" + xmlStr
					+ "]]></SvcCont></InterBoss>";

			exchange.sendResponseHeaders(200, xmlBody.getBytes("UTF-8").length);
			writer.write(xmlBody);

			writer.close();
			ut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param input
	 */
	@SuppressWarnings("unused")
	private void handleRequestBody(InputStream input) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input, "UTF-8"));

			String fieldSepaStr = null;
			while ((fieldSepaStr = reader.readLine()) != null) {
				handleField(reader, fieldSepaStr);
			}

			input.close();
			reader.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	private void handleField(BufferedReader reader, String fieldSepaStr) {

		try {
			int lineNumber = 0;
			String lineStr = null;
			String key = null;
			boolean valueFlag = false;
			StringBuffer value = new StringBuffer();
			while ((lineStr = reader.readLine()) != null) {
				lineNumber++;

				if (lineNumber == 1) {
					key = StringUtils.substringAfter(lineStr, "name=\"")
							.replace("\"", "");
					continue;
				}

				if (lineStr.equals(fieldSepaStr)) {
					handleField(reader, fieldSepaStr);
					System.out.println("-----------------fieldname=" + key);
					System.out.println("-----------------fieldvalue=["
							+ value.toString() + "]");
				}

				if (lineStr.equals(fieldSepaStr + "--")) {
					System.out.println("-----------------fieldname=" + key);
					System.out.println("-----------------fieldvalue=["
							+ value.toString() + "]");
					break;
				}

				if (StringUtils.isBlank(lineStr) && lineNumber == 4) {
					valueFlag = true;
					continue;
				}

				if (valueFlag) {
					value.append(lineStr + "\n");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void justPrint(InputStream input) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input, "UTF-8"));

			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println("line:" + line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

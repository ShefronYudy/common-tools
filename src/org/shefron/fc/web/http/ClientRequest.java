package org.shefron.fc.web.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class ClientRequest {

	public static void method1() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(
					"http://127.0.0.1:6060/myApp/req.xml");

			httppost.addHeader("content-type", "multipart/form-data");

			StringBuffer xmlHead = new StringBuffer();
			xmlHead.append("<reqinfo>");
			xmlHead.append("<username>admin</username>");
			xmlHead.append("<password>admin</password>");
			xmlHead.append("</reqinfo>");
			String headStr = "<head><content><![CDATA[" + xmlHead.toString()
					+ "]]></content></head>";

			httppost.addHeader("xmlhead", headStr);

			StringBuffer xmlBody = new StringBuffer();
			String xmlStr = "\n\t<reqinfo>\n\t<status>is it ok ?</status>\n</reqinfo>";
			xmlBody.append("<InterBoss>\n\t<SvcCont><![CDATA[" + xmlStr
					+ "]]></SvcCont>\n</InterBoss>");
			StringBody stringBody = new StringBody(xmlBody.toString(),
					ContentType.DEFAULT_TEXT.withCharset(Consts.UTF_8));
			StringBody stringBody2 = new StringBody("It just tests!haha",
					ContentType.DEFAULT_TEXT.withCharset(Consts.UTF_8));

			File file = new File("E:\\files\\upload.xml");
			FileBody fileBody = new FileBody(file,
					ContentType.APPLICATION_OCTET_STREAM
							.withCharset(Consts.UTF_8));

			// 传递数据
			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart(
					"xmlbody", stringBody).addPart("test", stringBody2)
					.addPart("upload.xml", fileBody).build();
			httppost.setEntity(reqEntity);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", "vip"));
			nvps.add(new BasicNameValuePair("password", "secret"));

			// 传递参数
			httppost.setEntity(new UrlEncodedFormEntity(nvps));

			httppost.setProtocolVersion(HttpVersion.HTTP_1_1);

			CloseableHttpResponse response = httpclient.execute(httppost);

			for (Header header : response.getHeaders("xmlhead")) {
				System.out.println(header.getName() + ":" + header.getValue());
			}

			StatusLine statusLine = response.getStatusLine();
			HttpEntity entity = response.getEntity();

			// Test
			// EntityUtils.consume(entity);

			if (statusLine.getStatusCode() >= 300) {
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			if (entity == null) {
				throw new ClientProtocolException(
						"Response contains no content");
			}
			try {
				ContentType contentType = ContentType.getOrDefault(entity);

				System.out.println("contentType=" + contentType.toString());
				Charset charset = contentType.getCharset();
				if (charset == null) {
					charset = Consts.ISO_8859_1;
				}
				System.out.println("charset:" + charset);

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent(), charset
								.name()));
				StringBuffer buffer = new StringBuffer();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						buffer.append(line + "\n");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				System.out.println("xmlbody=" + buffer.toString());

			} catch (Exception ex) {
				throw new IllegalStateException(ex);
			} finally {
				response.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		method1();
	}

}

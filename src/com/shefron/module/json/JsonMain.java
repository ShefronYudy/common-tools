package com.shefron.module.json;

public class JsonMain {

	public static void main(String[] args) {
		/** json中value值引号转义 */
		String value = "nihao\", \"hao";
		value = value.replace("\"", "\\\"");
		String jsonStr = "{\"key\":\""+value+"\"}";
		
		System.out.println("jsonStr:"+jsonStr);

	}

}

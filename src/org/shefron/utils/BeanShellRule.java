package org.shefron.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellRule {

	private Interpreter i;
	private String script;
	
	public BeanShellRule(){
		i = new Interpreter();
	}
	
	public Object handle(String fileLocation, Map<String, Object> parameters) throws IOException, EvalError{
		this.script = getFileContents(new File(fileLocation));

		Object a = null;
		i.set("return", a);
		for (Entry<String, Object> entry : parameters.entrySet()) {
			i.set(entry.getKey(), entry.getValue());
		}
		
		i.eval(script);
		return i.get("return");
	}
	
	private String getFileContents(File f) throws IOException {
		
		FileReader fr = new FileReader(f);
		StringBuffer sb = new StringBuffer();
		String line;
		BufferedReader br = new BufferedReader(fr);
		
		while( (line=br.readLine()) != null ) {
			sb.append(line + System.getProperty("line.separator"));
		}
		
		return sb.toString();
	}



}

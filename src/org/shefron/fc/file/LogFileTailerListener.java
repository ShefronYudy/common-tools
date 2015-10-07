package org.shefron.fc.file;

/**
 * 监听器 
 */
public interface LogFileTailerListener {
	void newLogFileLine(String line);

}

package org.shefron.fc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SplitMergeFile {

	private String splitDir = null;
	private String mergeDir = null;
	private String filePath = null;
	private int fileNum = 0;
	private int totalBytes = 0;

	public SplitMergeFile(String filePath, int sizeK) throws Exception {
		if (filePath == null) {
			throw new Exception("filepath must not null");
		}
		this.filePath = filePath;
		init(sizeK);
	}

	public SplitMergeFile(String filePath, String splitDir, int sizeK)
			throws Exception {
		this.filePath = filePath;
		this.splitDir = splitDir;
		init(sizeK);
	}

	private void init(int sizeK) throws Exception {
		mergeDir = new File(filePath).getParent();
		splitDir = (splitDir == null ? mergeDir + "/split" : splitDir);

		File _splitDir = new File(splitDir);
		if (!_splitDir.exists()) {
			_splitDir.mkdirs();
		}
		File orgFile = new File(filePath);
		FileInputStream fileIs = new FileInputStream(orgFile);
		this.totalBytes = fileIs.available();
		this.fileNum = (totalBytes % (sizeK * 1024) == 0) ? (totalBytes / (sizeK * 1024))
				: (totalBytes / (sizeK * 1024)) + 1;
	}

	public void splitFile(int sizeK) throws Exception {
		File orgFile = new File(filePath);
		FileInputStream fileIs = new FileInputStream(orgFile);
		int fileBytes = sizeK * 1024;
		int i = 1;
		FileOutputStream fileOs = null;
		byte[] readbytes = new byte[fileBytes];
		int leftByteLen = totalBytes - (fileBytes * (fileNum - 1));

		for (; i <= fileNum; i++) {
			fileOs = new FileOutputStream(splitDir + "/tmp" + i + ".out", false);
			if (i != fileNum) {
				fileIs.read(readbytes);
				fileOs.write(readbytes);
				fileOs.flush();
				fileOs.close();
				continue;
			}
			byte[] leftBytes = new byte[leftByteLen];
			fileIs.read(leftBytes);
			fileOs.write(leftBytes);
			fileOs.flush();
			fileOs.close();
		}
		fileIs.close();
	}

	public void mergeFiles() throws Exception {
		String mergeFile = mergeDir + "/merge";

		File _mergeFile = new File(mergeFile);
		if (!_mergeFile.exists()) {
			_mergeFile.mkdirs();
		}

		FileOutputStream outFile = new FileOutputStream(mergeFile + "/NEW_"
				+ (new File(filePath).getName()), false);
		int i = 1;
		FileInputStream item = null;
		byte[] bytes = null;

		String splitFile = null;
		for (; i <= fileNum; i++) {
			splitFile = splitDir + "/tmp" + i + ".out";
			item = new FileInputStream(splitFile);
			bytes = new byte[item.available()];
			item.read(bytes);
			outFile.write(bytes);
			outFile.flush();
			item.close();
			new File(splitFile).delete();
		}
		outFile.flush();
		outFile.close();

		File delDir = new File(splitDir);
		if (delDir.exists()) {
			delDir.delete();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SplitMergeFile demo = new SplitMergeFile("E:/temp/TestDoc.doc", 2048);
		// demo.splitFile(2048);
		demo.mergeFiles();

	}

}

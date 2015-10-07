package org.shefron.fc.file;

import java.io.File;

public class MoveFile {

	/**
	 * 
	 * @param srcpath
	 *            /tlqnew/file/temp/RPTSR_371_01MO_20140101_000_000.xml
	 * @param destdir
	 *            /tlqnew/file/sndfile
	 * @throws Exception
	 */
	private static void moveFile2(String srcpath, String destdir)
			throws Exception {
		Runtime.getRuntime().exec("mv " + srcpath + " " + destdir);
	}

	private static void moveFile1(String srcpath, String destpath)
			throws Exception {
		File orifile = new File(srcpath);

		File dfile = new File(destpath);

		if (!dfile.getParentFile().exists()) {
			dfile.getParentFile().mkdirs();
		}
		if (dfile.exists()) {
			dfile.delete();
		}

		orifile.renameTo(dfile);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String type = args[0];

		String srcpath = args[1];
		String destpath = args[2];

		System.out.println("type:" + type + ",srcpath:" + srcpath
				+ ",destpath:" + destpath);

		if ("1".equals(type)) {
			try {
				moveFile1(srcpath, destpath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				moveFile2(srcpath, destpath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

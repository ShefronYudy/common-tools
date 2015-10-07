package org.shefron.fc.jmx;

import java.io.File;
import java.io.IOException;

public class FileCopierImpl implements FileCopier {

	@Override
	public void copyFile(String srcDir, String destDir, String filename)
			throws IOException {
		
		File srcFile = new File(srcDir,filename);
		File destFile = new File(destDir,filename);
		
		srcFile.renameTo(destFile);
		
	}

}

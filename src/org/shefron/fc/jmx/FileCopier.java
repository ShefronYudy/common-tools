package org.shefron.fc.jmx;

import java.io.IOException;

public interface FileCopier {
	
	public void copyFile(String drcDir,String destDir,String filename) throws IOException;

}

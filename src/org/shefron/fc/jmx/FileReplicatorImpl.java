package org.shefron.fc.jmx;

import java.io.File;
import java.io.IOException;

public class FileReplicatorImpl implements FileReplicator {

	private String srcDir;
	private String destDir;
	private FileCopier fileCopier;
	
	@Override
	public String getSrcDir() {
		return this.srcDir;
	}

	@Override
	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	@Override
	public String getDestDir() {
		return this.destDir;
	}

	@Override
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	
	public void setFileCopier(FileCopier fileCopier){
		this.fileCopier = fileCopier;
	}

	@Override
	public synchronized void replicate() throws IOException {
		File[] files = new File(srcDir).listFiles();
		for(File file :files){
			if(file.isFile()){
				fileCopier.copyFile(srcDir, destDir, file.getName());
			}
		}
	}

}

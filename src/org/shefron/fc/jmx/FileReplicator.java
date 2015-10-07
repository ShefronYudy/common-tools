package org.shefron.fc.jmx;

import java.io.IOException;

/**
 * 文件复制（重复）
 * @author Administrator
 *
 */
public interface FileReplicator {
	
	public String getSrcDir();
	public void setSrcDir(String srcDir);
	
	public String getDestDir();
	public void setDestDir(String destDir);
	
	public void setFileCopier(FileCopier fileCopier);
	
	public void replicate() throws IOException;

}

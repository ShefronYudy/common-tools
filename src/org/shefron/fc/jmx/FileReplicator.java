package org.shefron.fc.jmx;

import java.io.IOException;

/**
 * �ļ����ƣ��ظ���
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

package org.shefron.fc.file;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilterImpl implements FilenameFilter {

	public boolean accept(File dir, String name) {
		File file = new File(name);
		if (file.getName().indexOf("app") > 0) {
			return true;
		}
		return false;
	}

}

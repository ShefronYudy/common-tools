package org.shefron.fc.resourcebundle;

import java.util.ResourceBundle;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 方式一：资源class文件
		String baseName = "org.shefron.fc.resourcebundle.AboutResources";
		ResourceBundle bundle = ResourceBundleWrapper.getBundle(baseName);

		System.out.println(bundle.getString("about-frame.tab.about"));
		System.out.println(bundle.getObject("about-frame.tab.test"));

		// 方式二：资源文件properties
		ResourceBundle bundleFile = ResourceBundleWrapper
				.getBundle("org.shefron.fc.resourcebundle.LocalizationBundle");
		System.out.println(bundleFile.getString("Attributes"));

	}

}

package org.shefron.fc.resourcebundle;

import java.util.ResourceBundle;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ��ʽһ����Դclass�ļ�
		String baseName = "org.shefron.fc.resourcebundle.AboutResources";
		ResourceBundle bundle = ResourceBundleWrapper.getBundle(baseName);

		System.out.println(bundle.getString("about-frame.tab.about"));
		System.out.println(bundle.getObject("about-frame.tab.test"));

		// ��ʽ������Դ�ļ�properties
		ResourceBundle bundleFile = ResourceBundleWrapper
				.getBundle("org.shefron.fc.resourcebundle.LocalizationBundle");
		System.out.println(bundleFile.getString("Attributes"));

	}

}

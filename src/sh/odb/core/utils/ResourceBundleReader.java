package sh.odb.core.utils;

import java.util.ResourceBundle;

public class ResourceBundleReader {

	public final static Object initLock = new Object();

	private final static String PROPERTIES_FILE_NAME = "config-basic";

	private static ResourceBundle bundle = null;

	static {
		try {
			if (bundle == null) {
				synchronized (initLock) {
					if (bundle == null)
						bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
				}
			}
		} catch (Exception e) {
			System.out.println("读取资源文件property_zh.properties失败!");
		}
	}

	public static ResourceBundle getBundle() {
		return bundle;
	}

	public static void setBundle(ResourceBundle bundle) {
		bundle = bundle;
	}

	public static void main(String arg[]) {
		System.out.println(ResourceBundleReader.getBundle().getString("SourceDomain"));
	}

}
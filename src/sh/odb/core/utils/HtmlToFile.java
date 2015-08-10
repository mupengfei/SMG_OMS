package sh.odb.core.utils;

import java.io.IOException;

public class HtmlToFile {

	private static String URI = "C:\\Program Files\\wkhtmltopdf\\";

	public static void html2Image(String url, String path) throws IOException {
		String command = URI + "wkhtmltoimage.exe " + url + " " + path;
		runCommand(command);

	}

	public static void html2Pdf(String url, String path) throws IOException {
		String command = URI + "wkhtmltopdf.exe " + url + " " + path;
		runCommand(command);
	}

	private static void runCommand(String command) throws IOException {
		Runtime.getRuntime().exec(command);
	}
}

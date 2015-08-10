package sh.odb.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.channels.FileChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileFactory {

	private static final Log log = LogFactory.getLog(FileFactory.class);

	// 创建文件
	public static void creatFile(String fileName, String text) throws IOException {
		log.info("创建文件:" + fileName);
		File file = new File(fileName);
		file.getParentFile().mkdirs();// 创建目录.mkdir(),创建单层目录，mkdirs()循环创建目录
		OutputStream os = new FileOutputStream(file);
		os.write(text.getBytes("UTF-8"));
		os.close();
	}

	// 创建文件
	public static FileOutputStream creatFile(String fileName) throws IOException {
		log.info(fileName);
		File file = new File(fileName);
		file.getParentFile().mkdirs();// 创建目录.mkdir(),创建单层目录，mkdirs()循环创建目录
		return new FileOutputStream(file);
	}

	// 创建文件
	public static File getFileObject(String fileName) throws IOException {

		File file = new File(fileName);
		file.getParentFile().mkdirs();// 创建目录.mkdir(),创建单层目录，mkdirs()循环创建目录
		return file;
	}

	// 去除文件地址头部file:/
	public static String byUrlGetPath(URL url) {
		String address = url.toString();
		if (address.startsWith("file:/")) {
			address = address.substring(6, address.length());
		}
		return address;
	}

	public static String getFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String str = "";
		try {
			reader = getBufferedReader(path);
			String tempbyte = "";
			int line = 1;
			while ((tempbyte = reader.readLine()) != null) {
				str += tempbyte;
			}
			reader.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return str;
	}

	public static BufferedReader getBufferedReader(String path) throws UnsupportedEncodingException, FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
	}

	public static OutputStreamWriter getBufferedWriter(String path) throws UnsupportedEncodingException, FileNotFoundException {
		return new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
	}

	public static long copyFile(String srcDir, String destDir, String newFileName) throws IOException {
		File srcFile = new File(srcDir, newFileName);
		File destFile = new File(getFileObject(destDir), newFileName);
		FileChannel fcin = new FileInputStream(srcFile).getChannel();
		FileChannel fcout = new FileOutputStream(destFile).getChannel();
		long size = fcin.size();
		fcin.transferTo(0, fcin.size(), fcout);
		fcin.close();
		fcout.close();
		return size;
	}

}

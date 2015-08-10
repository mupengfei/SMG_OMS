package sh.odb.plugin.media.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;
import sh.odb.plugin.media.utils.FtpClient;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class ArrayFTPTask extends AbstractTask {

	private int bufSize = 1024 * 4;
	FtpClient ftp;
	private int timeout = 3600000;
	FileInputStream input;

	@Override
	protected String execut() throws Exception {

		return uploadFile(super.getMeta("host"),
				java.lang.Integer.parseInt(super.getMeta("port")),
				super.getMeta("username"), super.getMeta("password"),
				super.getMeta("dir"));

	}

	private String uploadFile(String host, int port, String username,
			String password, String dir) throws Exception {
		try {
			if (dir.trim().equals(""))
				dir = "/";
			ftp = new FtpClient(host, port, username, password);
			ftp.connect();
			ftp.mkDir(dir);
			upload();
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return dir;
	}

	public void upload() throws Exception {

		int count = java.lang.Integer.parseInt(super.getMeta("count"));
		for (int i = 0; i < count; i++) {
			Date stTime = new Date();
			String fileName = super.getMeta("target" + i);
			String source = super.getMeta("source" + i);
			File file = new File(source);
			if (!file.exists()) {
				throw new Exception("File is not exists:" + source);
			}
			log.info(fileName + "大小" + file.length() + " 开始于" + stTime);
			// input = new FileInputStream(file);
			ftp.upload(file, fileName);
			// input.close();
			long sumTime = new Date().getTime() - stTime.getTime();
			log.info(fileName + " 总耗时" + sumTime);

		}

	}

	// private boolean mkDir(String filepath) throws Exception {
	// ftp.makeDirectory(filepath);
	// ftp.changeWorkingDirectory(filepath);
	// return true;
	// }

	public int getBufSize() {
		return bufSize;
	}

	public void setBufSize(int bufSize) {
		this.bufSize = bufSize;
	}

	@Override
	public void done() throws Exception {
		ftp.disconnect();
	}

	public static void main(String args[]) throws Exception {

		ArrayFTPTask task = new ArrayFTPTask();
		MediaBean bean = new MediaBean();
		// bean.pushMeta("host", "222.68.17.215");
		// bean.pushMeta("port", "21");
		// bean.pushMeta("username", "ftpuser");
		// bean.pushMeta("password", "ftp!@#qwe");

		// bean.pushMeta("host", "localhost");
		// bean.pushMeta("port", "21");
		// bean.pushMeta("username", "ftpuser");
		// bean.pushMeta("password", "ftpuser");

		bean.pushMeta("host", "50.31.240.243");
		bean.pushMeta("port", "21");
		bean.pushMeta("username", "kankanews");
		bean.pushMeta("password", "SMGKankanews");

		bean.pushMeta("dir", "/test07_06");

		bean.pushMeta("count", "3");
		bean.pushMeta("source2", "I://test.txt");
		bean.pushMeta("target2", "test22.txt");
		bean.pushMeta("source1", "I://test3.txt");
		bean.pushMeta("target1", "test13.txt");
		bean.pushMeta("source0", "H://style_switch.xml");
		bean.pushMeta("target0", "style_switch.xml");
		task.setBean(bean);
		System.out.println(BeanFactory.toJSON(bean));
		task.execut();

	}
}

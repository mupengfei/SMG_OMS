package sh.odb.plugin.media.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import sh.odb.plugin.media.utils.FtpClient;

public class FTPTask extends AbstractTask {

	private int bufSize = 1024 * 4;
	private FtpClient ftp;
	private FileInputStream stream;
	private int timeout = 100;

	public FTPTask() {
	}

	@Override
	protected String execut() throws Exception {
		File file = new File(bean.getFileAddress());
		// File file = new File("D://test.txt");
		if (!file.exists()) {
			throw new Exception("File is not exists");
		}

		// stream = new FileInputStream(file);

		return uploadFile(super.getMeta("host"),
				java.lang.Integer.parseInt(super.getMeta("port")),
				super.getMeta("username"), super.getMeta("password"),
				super.getMeta("dir"), super.getMeta("file"), file);
	}

	@Override
	public void done() {

		ftp.disconnect();
	}

	public String uploadFile(String host, int port, String username,
			String password, String dir, String filename, File file)
			throws Exception {
		ftp = new FtpClient(host, port, username, password);
		ftp.connect();
		// connect(username,password,host,port);
		ftp.mkDir(dir);
		ftp.upload(file, filename);
		return dir;
	}

	public static void main(String[] args) {
		FTPTask ftp = new FTPTask();
		File file;
		try {
			file = new File("I://test3.txt");
			ftp.uploadFile("50.31.240.243", 21, "kankanews", "SMGKankanews",
					"/testsingle", "C_WinXP.GHO", file);
			// ftp.uploadFile("127.0.0.1", 21, "ftpuser", "ftpuser", "/test",
			// "C_WinXP.GHO", file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

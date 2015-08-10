package sh.odb.plugin.media.utils;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FtpClient {
	FTPClient client;

	private long transferred;// 已经传输的字节数
	private String ip;
	private int port;
	private String userName;
	private String passWord;
	private File file;
	private String fileName;
	private String dir;

	private MyTransferListener listener;

	private boolean isFailed = false;

	private boolean isCompleted = false;

	private Log log = LogFactory.getLog(getClass());;

	// private int bufSize = 1024 * 4;

	public FtpClient(String ip, int port, String userName, String passWord) {
		client = new FTPClient();
		listener = new MyTransferListener(this);
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		this.passWord = passWord;
	}

	public void connect() {
		client = new FTPClient();
		try {
			client.setPassive(false);
			client.connect(ip, port);
			client.login(userName, passWord);
			client.setCharset("gb2312");
			client.setType(FTPClient.TYPE_BINARY);
			// client.setControlEncoding("gb2312");
			// client.enterLocalPassiveMode();
			// client.setDefaultTimeout(timeout);
			// client.setDataTimeout(timeout);
			// client.setSoTimeout(timeout);
			// client.setBufferSize(bufSize);
			// client.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void disconnect() {
		try {
			client.disconnect(false);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void changeDir(String dir) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		this.dir = dir;
		client.changeDirectory(dir);
	}

	public void createDir(String dir) {
		this.dir = dir;
		try {
			client.createDirectory(dir);
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException e) {
			log.error(e);
		}
	}

	public void upload(File file, String fileName)
			throws IllegalStateException, FileNotFoundException, IOException,
			FTPIllegalReplyException, FTPDataTransferException, FTPException,
			FTPAbortedException, InterruptedException {
		this.file = file;
		this.fileName = fileName;
		if (isFailed)
			return;
		// client.upload(file, transferred, listener);
		log.info(file.length() + " " + transferred);
		try {
			client.upload(fileName, new FileInputStream(file), transferred,
					transferred, listener);
		} catch (FTPDataTransferException | NullPointerException e) {
			// TODO Auto-generated catch block
			log.error("upload" + e.getLocalizedMessage());
			if (this.isFailed)
				throw e;
		} catch (InterruptedException e) {
			throw e;
			// TODO: handle exception
		}
		log.info(file.length() + " end " + transferred);
	}

	public void setTransferred(long transferred) {
		this.transferred = transferred;
	}

	public boolean mkDir(String filepath) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		int isEx = isExist(filepath);
		if (isEx == -1) {
			createDir(filepath);
		}
		changeDir(filepath);
		return true;
	}

	public void reUpload() throws IllegalStateException, IOException,
			FTPIllegalReplyException, FTPException, FTPDataTransferException,
			FTPAbortedException, InterruptedException {
		log.info("进入重连模式");
		disconnect();// 首先安全退出
		connect();// 登陆
		changeDir(this.dir);// 切换目录
		upload(this.file, this.fileName);
	}

	public void setListener(MyTransferListener listener) {
		this.listener = listener;
	}

	public int isExist(String remotePath) {
		remotePath = formatPath4FTP(remotePath);
		int x = -1;
		FTPFile[] list = null;
		try {
			list = client.list(remotePath);
		} catch (Exception e) {
			log.error(e);
			return -1;
		}
		if (list.length > 1)
			return FTPFile.TYPE_DIRECTORY;
		else if (list.length == 1) {
			FTPFile f = list[0];
			if (f.getType() == FTPFile.TYPE_DIRECTORY)
				return FTPFile.TYPE_DIRECTORY;
			// 假设推理判断
			String _path = remotePath + "/" + f.getName();
			try {
				int y = client.list(_path).length;
				if (y == 1)
					return FTPFile.TYPE_DIRECTORY;
				else
					return FTPFile.TYPE_FILE;
			} catch (Exception e) {
				log.error(e);
				return FTPFile.TYPE_FILE;
			}
		} else {
			try {
				client.changeDirectory(dir);
				return FTPFile.TYPE_DIRECTORY;
			} catch (Exception e) {
				log.error(e);
				return -1;
			}
		}
	}

	public static String formatPath4FTP(String path) {
		String reg0 = "\\\\+";
		String reg = "\\\\+|/+";
		String temp = path.trim().replaceAll(reg0, "/");
		temp = temp.replaceAll(reg, "/");
		if (temp.length() > 1 && temp.endsWith("/")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		return temp;
	}

	public void setIsFailed(boolean isFailed) {
		// TODO Auto-generated method stub
		this.isFailed = isFailed;
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

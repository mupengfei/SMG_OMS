package sh.odb.plugin.media.message;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPTask extends AbstractTask {

	private ChannelSftp sftp  ; 
	
	@Override
	protected String execut() throws Exception {
		File file = new File(bean.getFileAddress());
		// File file = new File("D://test.txt");
		if (!file.exists()) {
			throw new Exception("File is not exists");
		}
		return uploadFile(super.getMeta("host"), java.lang.Integer.parseInt(super.getMeta("port")), super.getMeta("username"), super.getMeta("password"), super.getMeta("dir"), super.getMeta("file"), new FileInputStream(file));

	}
	
	
	@Override
	public void done() throws Exception {
		disconnect( );
	}
	
	
	

	private String uploadFile(String host, int port, String username, String password, String dir, String fileName, FileInputStream fileInputStream) throws Exception {
	
		try {
			connect( username, password, host, port);
			upload( dir, fileName, fileInputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			disconnect( );
		}
		return dir;
	}

	 

	/**
	 * connect server via sftp
	 * 
	 * @return
	 * @throws JSchException
	 */
	public ChannelSftp connect(  String username, String password, String host, int port) throws JSchException {
		if (sftp != null) {
			log.info("sftp is not null");
		}
		JSch jsch = new JSch(); 
		Session sshSession = jsch.getSession(username, host, port);
		log.info("Session created.");
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		log.info("Session connected.");
		log.info("Opening Channel.");
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		log.info("Connected to " + host + ".");
		return sftp;
	}

	public  void disconnect( ) throws JSchException {

		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
				log.info("sftp关闭连接！！！！！====" + sftp);
			} else if (sftp.isClosed()) {
			}
		}
		if (sftp.getSession() != null && sftp.getSession().isConnected()) {
			sftp.getSession().disconnect();
		}
		log.info("sftp 已经关闭");
	}

	/**
	 * upload all the files to the server
	 * 
	 * @param fileInputStream
	 * @param fileName
	 * @param dir
	 * @throws Exception
	 */
	public void upload( String dir, String fileName, FileInputStream fileInputStream) throws Exception {
		mkDir(dir);
		sftp.put(fileInputStream, fileName);
	}

	/**
	 * create Directory
	 * 
	 * @param filepath
	 * @param sftp
	 * @throws SftpException
	 */
	private Vector _list(String dir, ChannelSftp sftp) {
		try {
			return sftp.ls(dir);
		} catch (Exception e) {
			return null;
		}
	}

	private boolean mkDir(String filepath ) throws Exception {
		if(filepath.trim().equals("")  ){
			return true;
		}
		try {
			String path = filepath;
			if (path.indexOf("\\") != -1) {
				path = filepath.replaceAll("\\", "/");
			}
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length; i++) {
				String dir = paths[i];
				Vector ls = _list(dir, sftp);
				if (ls == null || ls.size() <= 0) {
					sftp.mkdir(dir);
				}
				sftp.cd(dir);
			}
		} catch (Exception e1) {
			throw e1;
		}
		return true;
	}

	public static void main(String args[]) throws Exception {
		SFTPTask task = new SFTPTask();
		task.uploadFile("68.169.42.100", 22, "kankanews", "SMGKankanews", "/peeply/test", "peeply.txt", new FileInputStream("D://test.txt"));
	}

	

}

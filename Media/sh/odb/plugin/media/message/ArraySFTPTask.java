package sh.odb.plugin.media.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class ArraySFTPTask extends AbstractTask {

	
	private   ChannelSftp  sftp  ; 
 
	 
	@Override
	protected String execut() throws Exception {
		return uploadFile(super.getMeta("host"), java.lang.Integer.parseInt(super.getMeta("port")), super.getMeta("username"), super.getMeta("password"), super.getMeta("dir") );
	}

	private String uploadFile(String host, int port, String username, String password, String dir) throws Exception {
		try {
			connect( username, password, host, port);
			upload( dir );
		} catch (Exception ex) {
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
	public void connect(  String username, String password, String host, int port) throws JSchException {
	
		 
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
	}

	public   void disconnect( ) throws JSchException {

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
	public void upload(  String dir) throws Exception {
		mkDir(dir);
		int count =  java.lang.Integer.parseInt(super.getMeta("count"));
		for(int i=0;i<count;i++ ){
			String fileName =  super.getMeta("target"+i);
			String source =  super.getMeta("source"+i);
			File file = new File(source);
			if (!file.exists()) {
				throw new Exception("File is not exists:"+source);
			}
			
			sftp.put( new FileInputStream(file), fileName);
		}
		
		
	}

	/**
	 * create Directory
	 * 
	 * @param filepath
	 * @param sftp
	 * @throws SftpException
	 */
	private Vector<?> _list(String dir, ChannelSftp sftp) {
		try {
			return sftp.ls(dir);
		} catch (Exception e) {
			return null;
		}
	}

	private boolean mkDir(String filepath ) throws Exception {
		try {
			String path = filepath;
			if (path.indexOf("\\") != -1) {
				path = filepath.replaceAll("\\", "/");
			}
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length; i++) {
				String dir = paths[i];
				Vector<?> ls = _list(dir, sftp);
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

	

	@Override
	public void done() throws Exception {
		disconnect( );
	}
	
	
	
	public static void main(String args[]) throws Exception {
		ArraySFTPTask task = new ArraySFTPTask();
		MediaBean bean = new MediaBean();
		bean.pushMeta("host", "68.169.42.100");
		bean.pushMeta("port", "22");
		bean.pushMeta("username", "kankanews");
		bean.pushMeta("password", "SMGKankanews");
		bean.pushMeta("dir", "test/peeply");
		
		
		
		bean.pushMeta("count","2");
		bean.pushMeta("source0", "D://test.txt");
		bean.pushMeta("target0", "test0.txt");
		bean.pushMeta("source1", "D://test.txt");
		bean.pushMeta("target1", "test1.txt");
		task.setBean(bean);
		
		
		System.out.println(BeanFactory.toJSON(bean));
		//task.execut();
		//task.uploadFile("68.169.42.100", 22, "kankanews", "SMGKankanews", "/peeply/test", "peeply.txt", new FileInputStream("D://test.txt"));
	}

}

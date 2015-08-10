package sh.odb.plugin.media.utils;

import java.io.IOException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class MyTransferListener implements FTPDataTransferListener {

	private FtpClient ftpClient;
	private int maxSize = 10;
	private int currentSize = 0;
	private int waitTime = 20;// 重连等待时间
	private long sumdate = 0;
	private Log log = LogFactory.getLog(getClass());;

	public MyTransferListener(FtpClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	@Override
	public void aborted() {
		System.out.println("aborted");

	}

	@Override
	public void completed() {
		System.out.println("completed");
		log.info(ftpClient.getFileName() + "completed");
		ftpClient.setTransferred(0);
		ftpClient.setCompleted(true);
		ftpClient.setIsFailed(false);
	}

	@Override
	public void failed() {
		log.error(ftpClient.getFileName() + "failed");
		if (currentSize < maxSize) {
			ftpClient.setIsFailed(false);
			try {
				Thread.sleep(waitTime * 1000);//
				ftpClient.reUpload();
			} catch (NullPointerException e) {
				ftpClient.setIsFailed(true);
				log.error(e.getLocalizedMessage());
			} catch (InterruptedException e) {
				ftpClient.setIsFailed(true);
				log.error(e.getLocalizedMessage());
			} catch (IllegalStateException e) {
				ftpClient.setIsFailed(true);
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			} catch (IOException e) {
				ftpClient.setIsFailed(true);
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			} catch (FTPIllegalReplyException e) {
				ftpClient.setIsFailed(true);
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			} catch (FTPException e) {
				ftpClient.setIsFailed(true);
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			} catch (FTPDataTransferException e) {
				ftpClient.setIsFailed(true);
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			} catch (FTPAbortedException e) {
				ftpClient.setIsFailed(true);
				// TODO Auto-generated catch block
				log.error(e.getLocalizedMessage());
			}
			currentSize++;
		} else {
			ftpClient.setIsFailed(true);
		}

	}

	@Override
	public void started() {
		System.out.println("started");
	}

	@Override
	public void transferred(int arg0) {
		sumdate = sumdate + arg0;
//		System.out.println(ftpClient.getFileName() + "当前速度:" + arg0 + " 已传输:"
//				+ sumdate);
		// log.info(ftpClient.getFileName() + "当前速度:" + arg0);
		ftpClient.setTransferred(sumdate);

	}

}

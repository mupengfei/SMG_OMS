package com.jms;

import javax.jms.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jms.receive.JmsReceiveListener;

import sh.odb.core.utils.SpringContextUtil;
import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;
import sh.odb.plugin.media.bean.ResultBean;

public class SMGFutureTask extends FutureTask {
	protected final Log log = LogFactory.getLog(getClass());

	private String message;
	private int code;
	private String startTime;
	private Date sTime;
	private JmsReceiveListener listener;
	
	@SuppressWarnings("unchecked")
	public SMGFutureTask(final MediaBean bean,final JmsReceiveListener listener) throws JsonGenerationException, JsonMappingException, IOException {
		super(new Callable<ResultBean>() { 
			public ResultBean call() throws InterruptedException  {
					return listener.onMessage(bean);
			}
		});
		this.message = BeanFactory.toJSON(bean);
		this.code = this.hashCode();
		sTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS");
		this.startTime = sf.format(sTime);
		this.listener = listener;
	}

	protected void done() {
		try {
			listener.done();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStartTime() {
		return startTime;
	}

	public long getConTime() {
		return new Date().getTime() - sTime.getTime();
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}

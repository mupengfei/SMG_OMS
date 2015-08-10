package com.jms.receive;

import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sh.odb.core.utils.SpringContextUtil;
import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;
import sh.odb.plugin.media.bean.ResultBean;
import sh.odb.plugin.media.message.AbstractTask;

public class JmsReceiveListener {

	
	private  AbstractTask task ;
	private Log log = LogFactory.getLog(getClass());;
	
	
	public ResultBean onMessage(MediaBean bean) {
		ResultBean resultBean = new ResultBean();
		try {
			resultBean.setId(bean.getId());
			task = getAbstractTask(bean);
			resultBean.setMessage(task.doTask());
		} catch (Exception e) {
			log.error("JmsReceiveListener" + e.getLocalizedMessage());
			resultBean.setStatus(0);
			resultBean.setMessage(e.toString());
		} finally {
			resultBean.setEvent(bean.getEvent());
		}
		return resultBean;
	}

	private AbstractTask getAbstractTask(MediaBean bean) throws Exception {
		try {
			AbstractTask task = (AbstractTask) SpringContextUtil.getBean(bean.getType());
			task.setBean(bean);
			return task;

		} catch (Exception ex) {
			throw new Exception("no this function :" + bean.getType());
		}
	}

	public void done() throws Exception {
		task.done();
		
	}

}

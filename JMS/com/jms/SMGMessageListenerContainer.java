package com.jms;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jms.producer.ResultProducer;
import com.jms.receive.JmsReceiveListener;
import com.jms.service.ActionResultService;

import sh.odb.core.utils.SpringContextUtil;
import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;
import sh.odb.plugin.media.bean.ResultBean;

public class SMGMessageListenerContainer extends SimpleMessageListenerContainer {
	protected final Log log = LogFactory.getLog(getClass());
	private ThreadPoolTaskExecutor futureTaskExecutor;
	private FutureManager futureManager;
	private long timeout;
	private ResultProducer producer;

	@Override
	protected MessageConsumer createListenerConsumer(final Session session)
			throws JMSException {

		Destination destination = getDestination();
		if (destination == null) {
			destination = resolveDestinationName(session, getDestinationName());
		}
		MessageConsumer consumer = createConsumer(session, destination);

		if (futureTaskExecutor != null) {
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(final Message message) {
					SMGFutureTask task = null;
					ResultBean resultBean = new ResultBean();
					MediaBean bean = null;
					try {
						TextMessage myMessage = formartMessage(message);
						String mtemp = myMessage.getText();
						bean = BeanFactory.creatMediaBean(mtemp);
						resultBean.setEvent(bean.getEvent());
						task = getFutureTask(bean);
					} catch (Exception e) {
						log.info(e.getMessage());
						resultBean.setId(bean.getId());
						resultBean.setEvent(bean.getEvent());
						resultBean.setStatus(0);
						resultBean.setMessage(e.toString());
						returnResult(resultBean);
						return;
					}

					futureManager.push(task);
					futureTaskExecutor.execute(task);

					try {
						if (timeout == 0) {
							resultBean = (ResultBean) task.get();
						} else {
							resultBean = (ResultBean) task.get(timeout,
									TimeUnit.MILLISECONDS);
						}
					} catch (InterruptedException e) {
						task.cancel(true);
						resultBean.setId(bean.getId());
						resultBean.setStatus(0);
						resultBean.setEvent(bean.getEvent());
						resultBean.setMessage(e.toString());
						log.error(e.getLocalizedMessage());
					} catch (TimeoutException e) {
						task.cancel(true);
						resultBean.setId(bean.getId());
						resultBean.setStatus(0);
						resultBean.setEvent(bean.getEvent());
						resultBean.setMessage(e.toString());
						log.error(e.getLocalizedMessage());
					} catch (Exception e) {
						resultBean.setId(bean.getId());
						resultBean.setStatus(0);
						resultBean.setEvent(bean.getEvent());
						resultBean.setMessage(e.toString());
						log.error(e.getLocalizedMessage());
					} finally {
						task.cancel(true);
						returnResult(resultBean);
						futureManager.remove(task);
					}
				}
			});
		}
		return consumer;
	}

	private TextMessage formartMessage(Message message) {
		if (message instanceof ActiveMQBytesMessage) {
			ActiveMQBytesMessage bm = (ActiveMQBytesMessage) message;
			byte[] bys = null;
			try {
				bys = new byte[(int) bm.getBodyLength()];
				bm.readBytes(bys);

				TextMessage mt = new org.apache.activemq.command.ActiveMQTextMessage();
				mt.setText(new String(bys));
				return mt;
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof TextMessage) {
			return (TextMessage) message;
		}
		return null;
	}

	public SMGFutureTask getFutureTask(final MediaBean bean)
			throws JMSException, JsonGenerationException, JsonMappingException,
			IOException {
		JmsReceiveListener listener = (JmsReceiveListener) SpringContextUtil
				.getBean("jmsReciveListener");
		SMGFutureTask future = new SMGFutureTask(bean, listener);
		return future;
	}

	private void returnResult(ResultBean bean) {
		getProducer().sendMessage(bean);
	}

	public ThreadPoolTaskExecutor getFutureTaskExecutor() {
		return futureTaskExecutor;
	}

	public void setFutureTaskExecutor(ThreadPoolTaskExecutor futureTaskExecutor) {
		this.futureTaskExecutor = futureTaskExecutor;
	}

	public FutureManager getFutureManager() {
		return futureManager;
	}

	public void setFutureManager(FutureManager futureManager) {
		this.futureManager = futureManager;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public ResultProducer getProducer() {
		return producer;
	}

	public void setProducer(ResultProducer producer) {
		this.producer = producer;
	}
}

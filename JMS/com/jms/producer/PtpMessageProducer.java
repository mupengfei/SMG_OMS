package com.jms.producer;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.jms.core.MessageCreator;

import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;

public class PtpMessageProducer extends MessageProducer {

	private Destination topic;

	private int i = 0;

	public void timmerSend() throws JsonGenerationException, JsonMappingException, IOException {

		for (int k = 0; k < 10; k++) {
			MediaBean bean = new MediaBean();
			bean.setId(i);
			bean.setEvent((k % 2 == 0 ? "WOWZA" : "CDN"));
			bean.setTitle("HelloWorld" + i);
			bean.setType("RSYNC");
			bean.setFileAddress("D:\\TDDOWNLOAD\\" + i + ".avi");
			bean.pushMeta("command", "cd   &&rsync sdfs>fd<fds\\>\\$|,>,<,$");
			bean.pushMeta("host", "localhost");
			bean.pushMeta("post", "21");
			bean.pushMeta("username", "test");
			bean.pushMeta("password", "test");
			bean.pushMeta("dir", "/");
			bean.pushMeta("file", i + ".avi");
			this.sendMessage(BeanFactory.creatMediaStr(bean));
			i++;
		}

	}

	public void sendMessage(final String topicMessage) {

		jmsTemplate.send(topic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(topicMessage);
				return message;
			}
		});
	}

	public void setTopic(Destination topic) {
		this.topic = topic;
	}

}

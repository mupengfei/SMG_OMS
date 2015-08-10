package com.jms.producer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

public class PublisherMessageProducer extends MessageProducer {

	private Destination topic;

	public void sendMessage(final String topicMessage) {
		jmsTemplate.send(topic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(topicMessage);
			}
		});
	}

	public void setTopic(Destination topic) {
		this.topic = topic;
	}

}

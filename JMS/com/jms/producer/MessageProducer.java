package com.jms.producer;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MessageProducer {

	protected JmsTemplate jmsTemplate;

	public void sendMessage(final Serializable obj, Destination destination) {
		jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject(obj);
				return message;
			}
		});
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}

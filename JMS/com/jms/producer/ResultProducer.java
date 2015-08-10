package com.jms.producer;

import java.util.Map;
import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.ResultBean;

public class ResultProducer {
	private Map<String, PublisherMessageProducer> producers;

	private PublisherMessageProducer defaultProducer;

	public void sendMessage(ResultBean bean) {
		PublisherMessageProducer producer = producers.get(bean.getEvent());
		try {
			producer = (producer == null ? defaultProducer : producer);
			producer.sendMessage(BeanFactory.toJSON(bean));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, PublisherMessageProducer> getProducers() {
		return producers;
	}

	public void setProducers(Map<String, PublisherMessageProducer> producers) {
		this.producers = producers;
	}

	public PublisherMessageProducer getDefaultProducer() {
		return defaultProducer;
	}

	public void setDefaultProducer(PublisherMessageProducer defaultProducer) {
		this.defaultProducer = defaultProducer;
	}

}

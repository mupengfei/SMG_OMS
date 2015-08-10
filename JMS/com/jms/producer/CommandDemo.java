package com.jms.producer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;

public class CommandDemo extends PtpMessageProducer {

	private int count = 2;
	private static int number=0;

	 
	public void timmerSend() throws JsonGenerationException, JsonMappingException, IOException {
				
		/*
		for (int i = 0; i < count; i++) {
			
			MediaBean bean  = new MediaBean(); 
			bean.setId( number );
			bean.setEvent("SFTP");
			bean.setFileAddress("D:\\IDE\\eclipse.zip");
			bean.setTitle("Test");
			bean.setType("SFTP");
			
			Map<String, String> metas = new HashMap<String,String>();
			metas.put("host", "114.80.151.109");
			metas.put("port", "2200");
			metas.put("username", "root");
			metas.put("password", "wangcai5388");
			metas.put("dir","");
			metas.put("file","eclipse"+number+".zip");
			bean.setMetas(metas );
			String command=BeanFactory.creatMediaStr( bean );
			this.sendMessage(command);

			i++;
			number++;
		}*/

	}

}

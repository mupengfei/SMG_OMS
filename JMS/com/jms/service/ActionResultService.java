package com.jms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.sun.xml.internal.stream.Entity;

import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.ResultBean;

public class ActionResultService {

	Queue<ResultBean> queue = new LinkedList<ResultBean>();
	private String url;

	public void sendMessage(ResultBean bean) {
		if (!sendMessage_Post(bean)) {
			queue.offer(bean);
		}
	}

	private boolean sendMessage_Post(ResultBean bean) {
		try {
			return post_comment(BeanFactory.toJSON(bean));
		} catch (Exception e) {
			return false;
		}
	}

	private boolean post_comment(String str) throws ClientProtocolException, IOException {

		// 构造一个post对象
		HttpPost httpPost = new HttpPost(url);
		// 添加所需要的post内容
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		System.out.println(str);
		nvps.add(new BasicNameValuePair("str", str));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		HttpResponse response = new DefaultHttpClient().execute(httpPost);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println("Response content length: " + entity.getContentLength());
		}

		// 显示结果
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\r\n");
		}
		if (entity != null) {
			entity.consumeContent();
		}
		if (sb.toString().trim().equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public void quartzSendMessage() {
		ResultBean bean;
		while ((bean = queue.peek()) != null) {
			if (sendMessage_Post(bean)) {
				queue.poll();
			} else {
				System.out.println("  post error ");
				break;
			}
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

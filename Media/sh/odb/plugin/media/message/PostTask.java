package sh.odb.plugin.media.message;



import java.util.concurrent.TimeUnit;

import sh.odb.plugin.media.bean.BeanFactory;
import sh.odb.plugin.media.bean.MediaBean;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Response;

public class PostTask extends AbstractTask {

	private int maxSize = 3;
	private long sleepTime =  5;
	private long timeout = 5;
	
	private AsyncHttpClient client;
	

	

	@Override
	protected String execut() throws Exception {
		client = new AsyncHttpClient();
		return postMessage(super.getMeta("url"), super.getMeta("message"));
	}
	
	@Override
	public void done() throws Exception {
		if( client != null  && ! client.isClosed()  ){
			client.close();
		}
		
	}

	private String postMessage(String url, String message) throws Exception {
		
		BoundRequestBuilder rb = client.preparePost(url);

		boolean result = false;
		Exception exception = null;
		String respons = null;
		for (int i = 0; i < maxSize; i++) {

			try {
				Response f = rb.setBody(message).execute().get(timeout, TimeUnit.SECONDS);
				respons = f.getResponseBody();
			} catch (Exception e) {
				exception = e;
				try {
					Thread.sleep(sleepTime*1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
			result = true;
			break;

		}
		
		if (result) {

			return respons;
		} else if (exception != null) {
			throw exception;
		}
		return null;
	}
	
	
	
	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public static void main(String args[]) throws Exception {

		PostTask test = new PostTask();
		
		MediaBean bean = new MediaBean();
		bean.pushMeta("url", "http://www.baifdddu1.com");
		bean.pushMeta("message", "<bean id=\"POST\" class=\"sh.odb.plugin.media.message.PostTask\"  scope=\"prototype\"><property name=\"maxSize\" value=\"3\" ></property>             <!--    失败重复次数 --><property name=\"sleepTime\" value=\"3\" ></property>           <!--    失败重复间隔时间   单位为 秒  （特别注意 单位为秒）  --><property name=\"timeout\" value=\"3\" ></property>             <!--    Post 超时时间   单位为 秒  （特别注意 单位为秒）   --></bean>");
		
		
		String jsonMessage = BeanFactory.creatMediaStr(bean);
		System.out.println(	jsonMessage);
		
		bean = BeanFactory.creatMediaBean( jsonMessage );
		System.out.println(	bean.getMetas().get("message"));
		
		
		
		
		
		//test.setBean(bean );
		//System.out.println(test.execut());
	}

	

}

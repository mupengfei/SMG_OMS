package sh.odb.plugin.media.message;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import sh.odb.plugin.media.bean.MediaBean;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

public class GetTask extends AbstractTask {
	private AsyncHttpClient client;

	private int maxSize = 3;
	private long sleepTime = 5;
	private long timeout = 5;

	@Override
	// "ParasXml=" +
	protected String execut() throws Exception {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < Integer.parseInt(super.getMeta("ParamCount")); i++) {
			buf.append(super.getMeta("Param" + i));
			buf.append("=");
			buf.append(URLEncoder.encode(super.getMeta("Value" + i)));
		}

		BoundRequestBuilder br = client.prepareGet(super.getMeta("url") + "?"
				+ buf);
		Response resp = br.execute().get(timeout, TimeUnit.SECONDS);
		String result = resp.getResponseBody();
		return result;
	}

	@Override
	public void done() throws Exception {
		if (client != null && !client.isClosed()) {
			client.close();
		}
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

	public static void main(String[] args) throws Exception {
		GetTask task = new GetTask();
		MediaBean bean = new MediaBean();
		bean.pushMeta("url",
				"http://218.242.251.203/services/ShJaWebservice/AddSPXWVideo");
		// bean.pushMeta("message", "ParasXml=" + Axis2Task.testXml);
		bean.pushMeta("ParamCount", "1");
		bean.pushMeta("Param0", "ParasXml");
		bean.pushMeta("Value0", Axis2Task.testXml);
		task.setBean(bean);
		System.out.println(task.execut());
	}
}

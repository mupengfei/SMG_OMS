package sh.odb.plugin.media.message;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sh.odb.plugin.media.bean.MediaBean;

public abstract class AbstractTask {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	MediaBean bean;

	public AbstractTask() {
	}

	public MediaBean getBean() {
		return bean;
	}

	public void setBean(MediaBean bean) {
		this.bean = bean;
	}

	public String doTask() throws Exception {
		return execut();
	}

	public String getMeta(String key) {
		return bean.getMetas().get(key);

	}

	protected abstract String execut() throws Exception;

	public abstract void done() throws Exception;
}

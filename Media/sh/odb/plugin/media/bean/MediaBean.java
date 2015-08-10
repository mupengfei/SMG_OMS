package sh.odb.plugin.media.bean;

import java.util.HashMap;
import java.util.Map;

public class MediaBean {
	private long id;
	private String type;
	private String title;
	private String fileAddress;
	private Map<String, String> metas;
	private String event;

	public MediaBean() {
		metas = new HashMap<String, String>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String> getMetas() {
		return metas;
	}

	public void setMetas(Map<String, String> metas) {
		this.metas = metas;
	}

	public void pushMeta(String key, String value) {
		if (metas == null) {
			metas = new HashMap<String, String>();
		}
		metas.put(key, value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}

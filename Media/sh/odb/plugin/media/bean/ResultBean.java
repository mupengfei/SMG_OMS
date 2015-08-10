package sh.odb.plugin.media.bean;

public class ResultBean {
	private long id;
	private int status = 1;
	private String message;
	private String event;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}

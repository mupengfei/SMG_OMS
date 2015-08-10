package com.jms;

import java.util.Vector;

public class FutureManager {

	Vector<SMGFutureTask> list;

	public FutureManager() {
		list = new Vector<SMGFutureTask>();
	}

	public void close(int hashCode) {
		SMGFutureTask taskTem = null;
		for (SMGFutureTask task : list) {
			if (task.hashCode() == hashCode) {
				taskTem = task;
				break;
			}
		}
		close(taskTem);
	}

	public void close(SMGFutureTask taskTem) {
		if (taskTem != null)
			taskTem.cancel(true);
	}

	public void push(SMGFutureTask task) {
		list.add(task);
	}

	public void remove(SMGFutureTask task) {
		list.remove(task);
	}

	public Vector<SMGFutureTask> getList() {
		return list;
	}

	public void setList(Vector<SMGFutureTask> list) {
		this.list = list;
	}

}

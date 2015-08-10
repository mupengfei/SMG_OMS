package sh.odb.core.utils.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchService {

	CountDownLatch latch;
	ThreadPoolService threadPoolService;
	List<CDThread> list;

	public CountDownLatchService(List<CDThread> list) {
		this(10, list);
	}

	public CountDownLatchService(int poolSize, List<CDThread> list) {
		this.list = list;
		latch = new CountDownLatch(list.size());
		threadPoolService = new ThreadPoolService(10);
	}

	public void start() {
		for (CDThread task : list) {
			task.setSignal(latch);
			threadPoolService.execute(task);
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadPoolService.destoryExecutorService(1000);
	}

}

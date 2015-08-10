package sh.odb.core.utils.thread;

import java.util.concurrent.CountDownLatch;

public interface CDThread extends Runnable {

	void setSignal(CountDownLatch latch);

}

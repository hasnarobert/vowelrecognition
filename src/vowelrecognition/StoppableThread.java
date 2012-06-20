package vowelrecognition;

public class StoppableThread extends Thread {
	private boolean stopFlag = false;

	protected synchronized boolean shouldStop() {
		return stopFlag;
	}

	public void stopThread() {
		stopFlag = true;
		while (this.isAlive()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				System.err
						.println("Could not put thread to sleep. Busy waiting performed.");
			}
		}
	}
}

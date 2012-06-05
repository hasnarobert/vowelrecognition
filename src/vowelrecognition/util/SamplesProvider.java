package vowelrecognition.util;

import vowelrecognition.core.SampledAudio;

public abstract class SamplesProvider {
	private SampledAudio samples;
	private boolean stopFlag;

	public void startThread() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (shouldStop() == false) {
					action();
				}
			}
		};

		(new Thread(runnable)).start();

	}

	public synchronized void stopThread() {
		stopFlag = true;
	}

	public SampledAudio getSamples() {

		SampledAudio samples = null;
		while (true) {
			samples = privateGetSamples();
			if (samples != null || shouldStop())
				break;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.err.println("Could not put SamplesProvider to sleep. "
						+ "Busy waiting is performed.");
			}
		}

		return samples;
	}

	private synchronized SampledAudio privateGetSamples() {
		SampledAudio toReturn = samples;
		this.samples = null;
		return toReturn;
	}

	protected synchronized void setSamples(SampledAudio samples) {
		this.samples = samples;
	}

	private synchronized boolean shouldStop() {
		return stopFlag;
	}

	protected abstract void action();
}

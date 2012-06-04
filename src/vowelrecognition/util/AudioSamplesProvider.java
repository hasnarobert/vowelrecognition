package vowelrecognition.util;

import vowelrecognition.core.SampledAudio;

public class AudioSamplesProvider {
	private SampledAudio samples;
	private boolean stopFlag = false;

	public void startThread() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (shouldStop() == false) {
					AudioRecorder ar = new AudioRecorder();
					try {
						ar.start();
						Thread.sleep(1000);
						ar.stop();
						setSamples(ar.getSamples());
					} catch (Exception e) {
						throw new RuntimeException(
								"AudioSamplesProvider is dead", e);
					}
				}
			}
		};

		(new Thread(runnable)).start();

	}

	public synchronized void stopThread() {
		stopFlag = true;
	}

	private synchronized SampledAudio privateGetSamples() {
		SampledAudio toReturn = samples;
		this.samples = null;
		return toReturn;
	}

	public SampledAudio getSamples() {
		SampledAudio samples = null;
		while (true) {
			samples = privateGetSamples();
			if (samples != null)
				break;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.err
						.println("Could not put AudioSamplesProvider to sleep. "
								+ "Busy waiting is performed.");
			}
		}

		return samples;
	}

	private synchronized void setSamples(SampledAudio samples) {
		this.samples = samples;
	}

	private synchronized boolean shouldStop() {
		return stopFlag;
	}
}

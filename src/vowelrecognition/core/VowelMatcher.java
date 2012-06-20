package vowelrecognition.core;

import vowelrecognition.traineddata.TrainedData;

public class VowelMatcher {
	private final TrainedData database;
	private final String[] vowels = new String[] { "A", "E", "I", "O", "U" };

	public VowelMatcher(TrainedData database) {
		this.database = database;
	}

	public String match(SampledAudio samples, String username)
			throws AudioAnalyzerException {
		class Counter {
			int count = 0;
			double[] pitch = new double[5];

			synchronized void increment() {
				count++;
			}

			synchronized int getCount() {
				return count;
			}

			synchronized void set(int index, double pitch_value) {
				pitch[index] = pitch_value;
			}

			synchronized double[] getPitch() {
				return pitch;
			}
		}

		class Matcher extends Thread {
			SampledAudio samples;
			int window_size;
			Counter cnt;

			Matcher(Counter cnt, SampledAudio samples, int window_size) {
				this.cnt = cnt;
				this.samples = samples;
				this.window_size = window_size;
			}

			@Override
			public void run() {
				double a = window_size * 0.1, b = window_size * 0.75, temp;
				int i = 0, ww = window_size;
				while (ww != 1) {
					++i;
					ww >>= 1;
				}
				i -= 6;
				try {
					temp = AudioAnalyzer.averagePitch(samples, window_size);
					temp = (temp - a) / (b - a) + 1;
					cnt.set(i, temp);
				} catch (Exception ex) {
				}
				cnt.increment();
			}
		}

		Counter cnt = new Counter();
		for (int i = 0; i < 5; ++i) {
			int windowSize = 1 << (i + 6);
			(new Matcher(cnt, samples, windowSize)).start();
		}

		while (cnt.getCount() != 5) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
			}
		}

		double[] diff = new double[vowels.length];
		double[] pitch = cnt.getPitch();

		for (int i = 0; i < vowels.length; ++i) {
			for (int j = 0; j < 5; ++j) {
				int windowSize = 1 << (j + 6);
				double a = windowSize * 0.1, b = windowSize * 0.75;
				double tmp = database.getUserData(username)
						.getFrameData(windowSize).getVowelData(vowels[i])
						.getAverage();
				tmp = (tmp - a) / (b - a) + 1;
				tmp -= pitch[j];
				tmp *= tmp;
				diff[i] += tmp;
			}
		}

		double min = -1;
		int index = -1;
		for (int i = 0; i < 5; ++i) {
			if (min == -1 || diff[i] < min) {
				min = diff[i];
				index = i;
			}
		}

		return vowels[index];
	}
}

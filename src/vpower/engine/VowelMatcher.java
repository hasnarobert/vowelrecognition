package vpower.engine;

import audioanalyzer.AudioAnalyzer;
import audioanalyzer.AudioAnalyzerException;
import audioanalyzer.WavFile;
import database.Database;

public class VowelMatcher {
	private final Database database;
	private final String[] vowels = new String[] { "A", "E", "I", "O", "U" };

	public VowelMatcher(Database database) {
		this.database = database;
	}

	public String match(WavFile file, String username)
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
			WavFile file;
			int window_size;
			Counter cnt;

			Matcher(Counter cnt, WavFile file, int window_size) {
				this.cnt = cnt;
				this.file = file;
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
					temp = AudioAnalyzer.averagePitch(file, window_size);
					temp = (temp - a) / (b - a) + 1;
					cnt.set(i, temp);
				} catch (Exception ex) {
				}
				cnt.increment();
			}
		}

		// long start = java.util.Calendar.getInstance().getTimeInMillis();
		// System.out.println("START : " + start);

		Counter cnt = new Counter();
		for (int i = 0; i < 5; ++i) {
			int windowSize = 1 << (i + 6);
			(new Matcher(cnt, file, windowSize)).start();
		}

		while (cnt.getCount() != 5) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
			}
		}
		// System.out.println("Fara rec : " +
		// (java.util.Calendar.getInstance().getTimeInMillis() - start));

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

		// System.out.println("Cu rec : " +
		// (java.util.Calendar.getInstance().getTimeInMillis() - start));
		return vowels[index];
	}
}

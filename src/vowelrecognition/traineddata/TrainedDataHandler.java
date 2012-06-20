package vowelrecognition.traineddata;

import vowelrecognition.core.AudioAnalyzer;
import vowelrecognition.core.AudioAnalyzerException;
import vowelrecognition.core.SampledAudio;

public class TrainedDataHandler {
	private final TrainedData database;

	public TrainedDataHandler(TrainedData database) {
		this.database = database;
	}

	public void addUser(String name) {
		database.addUser(name);
	}

	public void removeUser(String name) {
		database.removeUser(name);
	}

	public void train(SampledAudio samples, String vowel, String username)
			throws AudioAnalyzerException {
		class WavAdder extends Thread {
			VowelData vowel;
			int window_size;
			SampledAudio samples;

			WavAdder(VowelData vowel, SampledAudio samples, int window_size) {
				this.vowel = vowel;
				this.window_size = window_size;
				this.samples = samples;
			}

			@Override
			public void run() {
				double pitch = 0;
				try {
					pitch = AudioAnalyzer.averagePitch(samples, window_size);
				} catch (AudioAnalyzerException ex) {
					System.err
							.println("Error while computing the average pitch :"
									+ ex);
				}
				vowel.add(pitch);
				// System.out.println("Window size " + window_size + " -> "
				// + pitch + " pitch");
			}
		}

		UserData user = database.getUserData(username);

		for (int i = 6; i < 11; ++i) {
			int windowSize = 1 << i;
			(new WavAdder(user.getFrameData(windowSize).getVowelData(vowel),
					samples, windowSize)).start();
		}
	}

	public void clearDatabase() {
		database.clearData();
	}

	public void clearUserData(String name) {
		database.getUserData(name).clearData();
	}

	public void clearFrameData(String username, int frameSize) {
		database.getUserData(username).getFrameData(frameSize).clearData();
	}

	public void clearVowelData(String username, int frameSize, String vowelname) {
		database.getUserData(username).getFrameData(frameSize)
				.getVowelData(vowelname).clearData();
	}

	public TrainedData getDatabase() {
		return database;
	}
}

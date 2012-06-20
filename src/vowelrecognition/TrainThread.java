package vowelrecognition;

import java.awt.Color;

import vowelrecognition.core.SampledAudio;
import vowelrecognition.gui.MainWindow;
import vowelrecognition.traineddata.TrainedDataHandler;
import vowelrecognition.util.SpeechSamplesProvider;

public class TrainThread extends StoppableThread {

	private final SpeechSamplesProvider prov;
	private StoppableThread recognizeThread;
	private final TrainedDataHandler handler;
	private final MainWindow frame;

	public TrainThread(TrainedDataHandler handler, MainWindow frame) {
		this.prov = new SpeechSamplesProvider();
		this.handler = handler;
		this.frame = frame;
	}

	private void trainForVowel(String vowel) {
		System.out.println("Training vowel : " + vowel);
		SampledAudio samples;
		frame.litera.setText(vowel);

		samples = prov.getSamples();
		System.out.println("Speech recorded : " + samples.size());
		handler.train(samples, vowel, "User");

		frame.panel.setBackground(Color.GREEN);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.panel.setBackground(Color.WHITE);
	}

	@Override
	public void run() {
		recognizeThread = Main.recognizeThread;
		if (recognizeThread != null) {
			recognizeThread.stopThread();
		}

		System.out.println("TrainThread state : RUNNING");

		prov.startThread();

		trainForVowel("A");
		trainForVowel("A");
		trainForVowel("E");
		trainForVowel("E");
		trainForVowel("I");
		trainForVowel("I");
		trainForVowel("O");
		trainForVowel("O");
		trainForVowel("U");
		trainForVowel("U");

		prov.stopThread();
		frame.litera.setText("");

		System.out.println("TrainThread state : STOPPED");
	}

}
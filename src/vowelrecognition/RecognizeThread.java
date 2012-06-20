package vowelrecognition;

import vowelrecognition.core.AudioAnalyzerException;
import vowelrecognition.core.SampledAudio;
import vowelrecognition.core.VowelMatcher;
import vowelrecognition.gui.MainWindow;
import vowelrecognition.traineddata.TrainedDataHandler;
import vowelrecognition.util.SpeechSamplesProvider;

public class RecognizeThread extends StoppableThread {

	private final SpeechSamplesProvider prov;
	private StoppableThread trainThread;
	private final TrainedDataHandler handler;
	private final MainWindow frame;

	public RecognizeThread(TrainedDataHandler handler, MainWindow frame) {
		this.prov = new SpeechSamplesProvider();
		this.handler = handler;
		this.frame = frame;
	}

	@Override
	public void run() {
		trainThread = Main.trainThread;
		if (trainThread != null) {
			trainThread.stopThread();
		}

		System.out.println("RecognizeThread state : RUNNING");

		VowelMatcher matcher = new VowelMatcher(handler.getDatabase());
		prov.startThread();

		while (shouldStop() == false) {
			SampledAudio samples = prov.getSamples();
			if (shouldStop())
				break;
			try {
				frame.litera.setText(matcher.match(samples, "User"));
			} catch (AudioAnalyzerException ex) {
				System.err.println("Could not match sound " + ex.getMessage());
			}

		}

		prov.stopThread();

		System.out.println("RecognizeThread state : STOPPED");
	}
}
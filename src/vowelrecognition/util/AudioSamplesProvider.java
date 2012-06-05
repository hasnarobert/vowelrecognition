package vowelrecognition.util;


public class AudioSamplesProvider extends SamplesProvider {

	@Override
	protected void action() {
		AudioRecorder ar = new AudioRecorder();
		try {
			ar.start();
			Thread.sleep(1000);
			ar.stop();
			setSamples(ar.getSamples());
		} catch (Exception e) {
			throw new RuntimeException("AudioSamplesProvider is dead", e);
		}
	}
}

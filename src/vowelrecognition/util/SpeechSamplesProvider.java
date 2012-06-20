package vowelrecognition.util;

import vowelrecognition.core.AudioAnalyzer;
import vowelrecognition.core.SampledAudio;

public class SpeechSamplesProvider extends SamplesProvider {
	private final AudioSamplesProvider audioProvider;
	private SampledAudio currentSamples;
	private int left, right;

	public SpeechSamplesProvider() {
		audioProvider = new AudioSamplesProvider();
		currentSamples = null;
	}

	@Override
	public void startThread() {
		audioProvider.startThread();
		super.startThread();
	}

	@Override
	public void stopThread() {
		audioProvider.stopThread();
		super.stopThread();
	}

	@Override
	protected void action() {
		SampledAudio samplesToReturn = new SampledAudio();
		SampledAudio range;

		// skip over silence
		while (AudioAnalyzer.isSilence((range = getNextRangeOfSamples())))
			;

		// save all speech until silence occurs
		samplesToReturn.addAll(range);
		while (AudioAnalyzer.isSilence((range = getNextRangeOfSamples())) == false) {
			samplesToReturn.addAll(range);
			if (range.size() == 0)
				break;
		}

		if (samplesToReturn.size() >= 7168)
			setSamples(samplesToReturn);
	}

	private SampledAudio getNextRangeOfSamples() {
		SampledAudio range;

		if (currentSamples == null) {
			currentSamples = audioProvider.getSamples();
			left = 0;
		}

		right = left + 1024;
		if (right > currentSamples.size()) {
			SampledAudio newSamples = audioProvider.getSamples();

			if (newSamples == null)
				return new SampledAudio();

			currentSamples.addAll(newSamples);
			currentSamples = currentSamples.getSamplesInRange(left,
					currentSamples.size());
			left = 0;
			right = 1024;
		}

		range = currentSamples.getSamplesInRange(left, right);
		left = right;

		return range;
	}
}

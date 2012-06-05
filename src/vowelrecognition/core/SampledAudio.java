package vowelrecognition.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SampledAudio extends ArrayList<ComplexNumber> {
	private static final long serialVersionUID = 414911875972606451L;

	public SampledAudio() {
	}

	public SampledAudio(AudioInputStream ais)
			throws UnsupportedAudioFileException, IOException {
		AudioFormat af = ais.getFormat();

		if (af.getChannels() != 1) {
			throw new UnsupportedAudioFileException("WavFile is not mono");
		}
		if (af.getSampleSizeInBits() != 16) {
			throw new UnsupportedAudioFileException(
					"WavFile must have 16bit sample size");
		}

		while (ais.available() >= 2) {
			byte[] buff = new byte[2];
			ais.read(buff);
			int value = ((buff[1] & 0xff) << 8) + (buff[0] & 0xff);
			if ((value & (1 << 15)) != 0) {
				value -= (1 << 15);
				value *= -1;
			}
			this.add(new ComplexNumber(value));
		}
	}

	public SampledAudio(List<ComplexNumber> samples) {
		this.addAll(samples);
	}

	public SampledAudio(ComplexNumber[] samples) {
		this.addAll(ComplexNumber.getComplexArrayList(samples));
	}

	public SampledAudio(double[] samples) {
		this.addAll(ComplexNumber.getComplexArrayList(samples));
	}

	public SampledAudio getSamplesInRange(int left, int right) {
		SampledAudio tmp = new SampledAudio();
		for (int i = left; i < right && i < this.size(); ++i) {
			tmp.add(this.get(i));
		}
		return tmp;
	}
}

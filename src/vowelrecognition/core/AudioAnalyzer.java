package vowelrecognition.core;

public class AudioAnalyzer {

	public static SampledAudio fft(SampledAudio x)
			throws AudioAnalyzerException {
		int N = x.size();

		if (N == 1) {
			SampledAudio tmp = new SampledAudio();
			tmp.add(x.get(0));
			return tmp;
		}

		if (N % 2 != 0) {
			throw new AudioAnalyzerException(
					"SampledAudio`s size must be a power of 2");
		}

		SampledAudio even = new SampledAudio();
		for (int k = 0; k < N / 2; k++) {
			even.add(x.get(2 * k));
		}
		SampledAudio q = fft(even);

		SampledAudio odd = new SampledAudio();
		for (int k = 0; k < N / 2; k++) {
			odd.add(x.get(2 * k + 1));
		}
		SampledAudio r = fft(odd);

		ComplexNumber[] y = new ComplexNumber[N];
		for (int k = 0; k < N / 2; k++) {
			double kth = -2 * k * Math.PI / N;
			ComplexNumber wk = new ComplexNumber(Math.cos(kth), Math.sin(kth));
			y[k] = q.get(k).plus(wk.times(r.get(k)));
			y[k + N / 2] = q.get(k).minus(wk.times(r.get(k)));
		}
		return new SampledAudio(y);
	}

	public static SampledAudio ifft(SampledAudio x)
			throws AudioAnalyzerException {
		int N = x.size();
		SampledAudio y = new SampledAudio();

		for (int i = 0; i < N; i++) {
			y.add(x.get(i).conjugate());
		}

		y = fft(y);

		for (int i = 0; i < N; i++) {
			y.set(i, y.get(i).conjugate());
		}

		for (int i = 0; i < N; i++) {
			y.set(i, y.get(i).times(1.0 / N));
		}

		return y;
	}

	public static SampledAudio hammingWindow(SampledAudio samples) {
		SampledAudio tmp = new SampledAudio();
		for (int i = 0; i < samples.size(); ++i) {
			tmp.add(new ComplexNumber(samples.get(i).abs()
					* (0.54 - 0.46 * Math.cos(2 * Math.PI * i
							/ (samples.size() - 1))), 0));
		}
		return tmp;
	}

	public static SampledAudio cepstrum(SampledAudio samples)
			throws AudioAnalyzerException {
		SampledAudio tmp = new SampledAudio(samples);
		tmp = AudioAnalyzer.hammingWindow(tmp);
		tmp = AudioAnalyzer.fft(tmp);

		SampledAudio tmp2 = new SampledAudio();
		for (ComplexNumber x : tmp) {
			tmp2.add(new ComplexNumber(Math.log(x.abs()), 0));
		}

		tmp = AudioAnalyzer.ifft(tmp2);

		return tmp;
	}

	public static int pitch(SampledAudio samples) throws AudioAnalyzerException {
		SampledAudio tmp = AudioAnalyzer.cepstrum(samples);
		int left = (int) (tmp.size() * 0.1);
		int right = (int) (tmp.size() * 0.75);
		int index = -1;
		double max = -1;

		for (int i = left; i <= right; ++i) {
			if (max < tmp.get(i).abs()) {
				max = tmp.get(i).abs();
				index = i;
			}
		}

		return index;
	}

	public static double averagePitch(SampledAudio samples, int windowSize)
			throws AudioAnalyzerException {

		int N = windowSize, S = 0;

		while (N != 1) {
			if (N % 2 == 1)
				throw new AudioAnalyzerException(
						"windowSize must be a power of 2");
			N >>= 1;
		}

		for (int i = 0; i + windowSize < samples.size(); i += windowSize >> 1, ++N) {
			S += AudioAnalyzer.pitch(samples.getSamplesInRange(i, i
					+ windowSize));
		}

		return (double) S / N;
	}

	public static boolean isSilence(SampledAudio samples) {
		if (samples.size() == 0)
			return false;

		double silenceThreshold = 1000, maxim = samples.get(0).real();

		for (int i = 0; i < samples.size(); ++i) {
			if (samples.get(i).real() > maxim) {
				maxim = samples.get(i).real();
			}
		}

		return maxim < silenceThreshold;
	}
}

package vowelrecognition.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import vowelrecognition.core.SampledAudio;

public class AudioRecorder {
	private boolean recording;
	private final AudioFormat format;
	ByteArrayOutputStream baos;

	public AudioRecorder() {
		format = new AudioFormat(44100, 16, 1, true, false);
	}

	public void start() throws LineUnavailableException {
		startRecording();
		record();
	}

	public synchronized void stop() {
		recording = false;
	}

	private synchronized boolean isRecording() {
		return recording;
	}

	private synchronized void startRecording() {
		recording = true;
	}

	private void record() throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		final TargetDataLine target_data_line = (TargetDataLine) AudioSystem
				.getLine(info);
		target_data_line.open(format);
		target_data_line.start();

		Runnable runner = new Runnable() {
			byte buffer[] = new byte[1024];

			@Override
			public void run() {
				baos = new ByteArrayOutputStream();
				recording = true;
				try {
					while (isRecording()) {
						int count = target_data_line.read(buffer, 0,
								buffer.length);
						if (count > 0) {
							baos.write(buffer, 0, count);
						}
					}
					baos.close();
				} catch (IOException ex) {
					System.err.println("I/O problems: " + ex);
					System.exit(-1);
				}
			}
		};
		Thread captureThread = new Thread(runner);
		captureThread.start();
	}

	public SampledAudio getSamples() throws UnsupportedAudioFileException,
			IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		AudioInputStream ais = new AudioInputStream(bais, format,
				baos.toByteArray().length / format.getFrameSize());
		return new SampledAudio(ais);
	}
}
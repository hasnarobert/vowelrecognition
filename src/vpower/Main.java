/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpower;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import vowelrecognition.core.SampledAudio;
import vowelrecognition.core.VowelMatcher;
import vowelrecognition.traineddata.TrainedData;
import vowelrecognition.traineddata.TrainedDataHandler;
import vowelrecognition.util.SamplesProvider;
import vowelrecognition.util.SpeechSamplesProvider;

/**
 * 
 * @author ninu
 */
public class Main {

	public static void main(String[] args) throws Exception {
		File database_file = new File("database");
		TrainedData database = null;
		TrainedDataHandler handler;

		if (database_file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(database_file));
				database = (TrainedData) ois.readObject();
				ois.close();
			} catch (Exception ex) {
				System.err.println("Error at restoring the database : " + ex);
			}
		} else {
			database = new TrainedData();
		}
		handler = new TrainedDataHandler(database);

		VowelMatcher matcher = new VowelMatcher(database);

		SamplesProvider prov = new SpeechSamplesProvider();
		prov.startThread();

		int N = 10;
		while (N-- >= 0) {
			System.out.println("Speak now");
			SampledAudio samples = prov.getSamples();
			// System.out.println(samples.size() + "   "
			// + AudioAnalyzer.isSilence(samples));

			System.out.println("Litera este : "
					+ matcher.match(samples, "Ninu"));
		}

		prov.stopThread();

		// UI gui = new UI("vpower", handler);
		// gui.pack();
		// gui.setVisible(true);
	}
}

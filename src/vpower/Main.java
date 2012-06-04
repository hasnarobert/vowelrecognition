/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpower;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import vowelrecognition.traineddata.TrainedData;
import vowelrecognition.traineddata.TrainedDataHandler;
import vpower.gui.UI;

/**
 * 
 * @author ninu
 */
public class Main {

	public static void main(String[] args) {
		File database_file = new File("database.db");
		TrainedData database = null;
		TrainedDataHandler handler;
		;

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

		UI gui = new UI("vpower", handler);
		gui.pack();
		gui.setVisible(true);
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * while (true) { AudioRecorder ar = new AudioRecorder(); ar.start();
	 * Thread.sleep(1000); ar.stop();
	 * 
	 * WavFile wf = ar.getWavFile();
	 * System.out.println(AudioAnalyzer.isSilence(wf.getSamples())); } }
	 */
}

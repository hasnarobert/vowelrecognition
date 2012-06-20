package vowelrecognition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vowelrecognition.gui.MainWindow;
import vowelrecognition.traineddata.TrainedData;
import vowelrecognition.traineddata.TrainedDataHandler;

public class Main {

	public static StoppableThread recognizeThread, trainThread;

	public static void main(String[] args) {

		final MainWindow frame = new MainWindow();
		final TrainedData database = new TrainedData();
		final TrainedDataHandler handler = new TrainedDataHandler(database);

		frame.setVisible(true);
		database.addUser("User");

		frame.recunoastere.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (recognizeThread != null && recognizeThread.isAlive())
					return;
				recognizeThread = new RecognizeThread(handler, frame);
				recognizeThread.start();
			}
		});

		frame.antrenare.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (trainThread != null && trainThread.isAlive())
					return;
				trainThread = new TrainThread(handler, frame);
				trainThread.start();
			}
		});
	}
}

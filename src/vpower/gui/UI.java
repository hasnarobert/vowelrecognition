/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpower.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

import vowelrecognition.traineddata.TrainedDataHandler;

/**
 * 
 * @author ninu
 */
public class UI extends JFrame {
	private static final long serialVersionUID = -2313864962225161239L;
	Users users;
	Audio audio;

	public UI(String title, final TrainedDataHandler handler) {
		super(title);

		users = new Users(handler, this);
		audio = new Audio(handler, this);

		add(users, "North");
		add(audio, "South");

		for (String name : handler.getDatabase().getUsernames()) {
			users.username_combo.addItem(name);
		}
		users.username_combo.addItem("New User");

		audio.add_to_db_radio.setSelected(true);
		audio.recognize_button.setText("Add");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							new FileOutputStream(new File("database")));
					oos.writeObject(handler.getDatabase());
					oos.close();
				} catch (Exception ex) {
					System.err.println("Error at saving the database : " + ex);
				}
				System.exit(0);
			}
		});

	}
}

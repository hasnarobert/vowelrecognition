/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpower;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import vpower.engine.DatabaseHandler;
import vpower.gui.UI;
import database.Database;

/**
 * 
 * @author ninu
 */
public class Main {
	public static void main(String[] args) {
		File database_file = new File("database.db");
		Database database = null;
		DatabaseHandler handler;
		;

		if (database_file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(database_file));
				database = (Database) ois.readObject();
				ois.close();
			} catch (Exception ex) {
				System.err.println("Error at restoring the database : " + ex);
			}
		} else {
			database = new Database();
		}
		handler = new DatabaseHandler(database);

		UI gui = new UI("vpower", handler);
		gui.pack();
		gui.setVisible(true);
	}
}

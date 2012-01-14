package vpower.engine;

import audioanalyzer.AudioAnalyzer;
import audioanalyzer.AudioAnalyzerException;
import audioanalyzer.WavFile;
import database.Database;
import database.UserData;
import database.VowelData;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {
    private Database database;
    
    public DatabaseHandler(Database database) {
        this.database = database;
    }
    
    public void addUser(String name) {
        database.addUser(name);
    }
    
    public void removeUser(String name) {
        database.removeUser(name);
    }
    
    public void addWav(WavFile file, String vowel, String username) throws AudioAnalyzerException {
        class WavAdder extends Thread {
            VowelData vowel;
            int window_size;
            WavFile file;
            
            WavAdder(VowelData vowel, WavFile file, int window_size) {
                this.vowel = vowel;
                this.window_size = window_size;
                this.file = file;
            }
            
            @Override
            public void run() {
                double pitch = 0;
                try {
                    pitch = AudioAnalyzer.averagePitch(file, window_size);
                } catch (AudioAnalyzerException ex) {
                    System.err.println("Error while computing the average pitch :" + ex);
                }
                vowel.add(pitch);
                System.out.println("Window size " + window_size + " -> " + pitch + " pitch");
            }
        }
        
        UserData user = database.getUserData(username);
        
        for (int i = 6; i < 11; ++i) {
            int windowSize = 1 << i;            
            ( new WavAdder(user.getFrameData(windowSize).getVowelData(vowel), file, windowSize) ).start();
        }
    }
    
    public void removeWav(WavFile file, String vowel, String username) throws AudioAnalyzerException {
        UserData user = database.getUserData(username);
        
        for (int i = 6; i < 11; ++i) {
            int windowSize = 1 << i;
            double pitch = AudioAnalyzer.averagePitch(file, windowSize);
            user.getFrameData(windowSize).getVowelData(vowel).remove(pitch);
        }
    }
    
    public void clearDatabase() {
        database.clearData();
    }
    
    public void clearUserData(String name) {
        database.getUserData(name).clearData();
    }
    
    public void clearFrameData(String username, int frameSize) {
        database.getUserData(username).getFrameData(frameSize).clearData();
    }
    
    public void clearVowelData(String username, int frameSize, String vowelname) {
        database.getUserData(username).getFrameData(frameSize).getVowelData(vowelname).clearData();
    }
    
    public Database getDatabase() {
        return database;
    }
}

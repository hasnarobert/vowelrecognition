package audioanalyzer;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class WavFile {
    
    private AudioInputStream ais;
    private AudioFormat af;
    private SampledAudio samples;
   
    
    public WavFile(File file) throws UnsupportedAudioFileException, IOException {
        ais = AudioSystem.getAudioInputStream(file);
        computeWavFile();
    }
    
    public WavFile(AudioInputStream ais) throws UnsupportedAudioFileException, IOException {
        this.ais = ais;
        computeWavFile();
    }
    
    public SampledAudio getSamples() {
        return samples;
    }
    
    private void computeWavFile() throws UnsupportedAudioFileException, IOException {
        af = ais.getFormat();
        samples = new SampledAudio();
        
        if (af.getChannels() != 1) {
            throw new UnsupportedAudioFileException("WavFile is not mono");
        }
        if (af.getSampleSizeInBits() != 16) {
            throw new UnsupportedAudioFileException("WavFile must have 16bit sample size");
        }
        
        while (ais.available() >= 2) {
            byte[] buff = new byte[2];
            ais.read(buff);
            int value = ((buff[1] & 0xff) << 8) + (buff[0] & 0xff);
            if ((value & (1 << 15)) != 0) {
                value -= (1 << 15);
                value *= -1;
            }
            samples.add(new Complex(value));
        }
    }
}

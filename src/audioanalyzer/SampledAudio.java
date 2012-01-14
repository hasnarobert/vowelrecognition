package audioanalyzer;

import java.util.ArrayList;
import java.util.List;


public class SampledAudio extends ArrayList<Complex> {
    public SampledAudio() { }
    
    public SampledAudio(List<Complex> samples) {
        this.addAll(samples);
    }
    
    public SampledAudio(Complex[] samples) {
        this.addAll(Complex.getComplexArrayList(samples));
    }
    
    public SampledAudio(double[] samples) {
        this.addAll(Complex.getComplexArrayList(samples));
    }
    
    public SampledAudio getSamplesInRange(int left, int right) {
        SampledAudio tmp = new SampledAudio();
        for (int i = left; i < right && i < this.size(); ++i) {
            tmp.add(this.get(i));
        }
        return tmp;
    }
    
}

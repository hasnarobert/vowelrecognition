package vowelrecognition.traineddata;

import java.io.Serializable;
import java.util.ArrayList;

public class VowelData implements Serializable {
	private static final long serialVersionUID = 6293024400132417137L;
	private double S;
	private final String name;
	private final ArrayList<Double> pitch;

	public VowelData(String name) {
		this.name = name;
		pitch = new ArrayList<Double>();
		S = 0;
	}

	public synchronized void add(double x) {
		pitch.add(x);
		S += x;
	}

	public synchronized void remove(double x) {
		for (int i = 0; i < pitch.size(); ++i) {
			if (pitch.get(i) == x) {
				S -= x;
				pitch.remove(i);
				break;
			}
		}
	}

	public synchronized double getAverage() {
		return S / pitch.size();
	}

	public String getName() {
		return name;
	}

	public synchronized void clearData() {
		pitch.clear();
		S = 0;
	}
}

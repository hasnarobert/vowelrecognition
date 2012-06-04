package vowelrecognition.traineddata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FrameData implements Serializable {
	private static final long serialVersionUID = -4236663116953180602L;
	private final int size;
	private final Map<String, VowelData> vowels;

	public FrameData(int size) {
		this.size = size;
		vowels = new HashMap<String, VowelData>();
		vowels.put("A", new VowelData("A"));
		vowels.put("E", new VowelData("E"));
		vowels.put("I", new VowelData("I"));
		vowels.put("O", new VowelData("O"));
		vowels.put("U", new VowelData("U"));
	}

	public VowelData getVowelData(String name) {
		return vowels.get(name);
	}

	public int getSize() {
		return size;
	}

	public void clearData() {
		vowels.remove("A");
		vowels.remove("E");
		vowels.remove("I");
		vowels.remove("O");
		vowels.remove("U");
		vowels.put("A", new VowelData("A"));
		vowels.put("E", new VowelData("E"));
		vowels.put("I", new VowelData("I"));
		vowels.put("O", new VowelData("O"));
		vowels.put("U", new VowelData("U"));
	}
}

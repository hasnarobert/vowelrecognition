package vowelrecognition.traineddata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserData implements Serializable {
	private static final long serialVersionUID = 3967168709865741376L;
	private final String name;
	private final Map<Integer, FrameData> frames;

	public UserData(String name) {
		this.name = name;
		frames = new HashMap<Integer, FrameData>();
		frames.put(64, new FrameData(64));
		frames.put(128, new FrameData(128));
		frames.put(256, new FrameData(256));
		frames.put(512, new FrameData(512));
		frames.put(1024, new FrameData(1024));
	}

	public FrameData getFrameData(int size) {
		return frames.get(size);
	}

	public String getName() {
		return name;
	}

	public void clearData() {
		for (int i = 6; i < 11; ++i) {
			int windowSize = 1 << i;
			frames.remove(windowSize);
			frames.put(windowSize, new FrameData(windowSize));
		}
	}
}

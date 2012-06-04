package vowelrecognition.traineddata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TrainedData implements Serializable {
	private static final long serialVersionUID = 4488896584494035470L;
	private final Map<String, UserData> users;

	public TrainedData() {
		users = new HashMap<String, UserData>();
	}

	public void addUser(String name) {
		users.put(name, new UserData(name));
	}

	public void removeUser(String name) {
		users.remove(name);
	}

	public UserData getUserData(String name) {
		return users.get(name);
	}

	public void clearData() {
		users.clear();
	}

	public String[] getUsernames() {
		String[] tmp = new String[users.keySet().size()];
		int poz = 0;
		for (String name : users.keySet()) {
			tmp[poz++] = name;
		}

		return tmp;

	}
}

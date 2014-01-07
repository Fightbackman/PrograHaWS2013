package lufti.battleship;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author reidl
 */
public class Delay {

	private static HashMap<String,Integer> delays = new HashMap<String,Integer>();
	private static HashMap<String,Integer> counters = new HashMap<String,Integer>();
	
	public static void create(String name, int amount) {
		delays.put(name, amount);
		counters.put(name, 0);
	}
	
	public static boolean isDone(String name) {
		assert delays.containsKey("name");
		return counters.get(name) <= 0;
	}
	
	public static void setDelay(String name) {
		counters.put(name, delays.get(name));
	}
	
	public static void update() {
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			entry.setValue(Math.max(entry.getValue()-1,0));
		}
	}
}

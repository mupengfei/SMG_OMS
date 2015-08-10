package sh.odb.core.utils;

import java.util.HashMap;
import java.util.Map;

public class ControllerLocal {

	private static ThreadLocal<Map<String, Object>> cLocal = new ThreadLocal<Map<String, Object>>();

	public static Map<String, Object> getControllerLocal() {
		Map<String, Object> map = cLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			cLocal.set(map);
		}
		return map;
	}

}

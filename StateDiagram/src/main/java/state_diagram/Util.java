package state_diagram;

import java.awt.Point;

import org.json.JSONObject;

public class Util {
	public static Point pointFromJSON(JSONObject ob) {
		return new Point(ob.getInt("x"),ob.getInt("y"));
	}
}

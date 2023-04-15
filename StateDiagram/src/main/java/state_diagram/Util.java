package state_diagram;

import java.awt.Point;

import org.json.JSONObject;

public class Util {
	public static Point pointFromJSON(JSONObject ob) {
		return new Point(ob.getInt("x"),ob.getInt("y"));
	}
	public static double distance(Point p, Point a, Point b) {
        double baX = b.x - a.x;
        double baY = b.y - a.y;
        double paX = p.x - a.x;
        double paY = p.y - a.y;
        double d = Math.abs(baX * paY - baY * paX) / length(baX, baY);
        return d;
    }

    public static double length(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

}

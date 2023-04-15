package state_diagram.product;

public class Functions {
public static void houseAction19() {
System.out.println("house");
}
public static void restaurantAction45() {
System.out.println("restaurant");
}
public static void barAction58() {
System.out.println("bar");
}
public static boolean transition71(Test t) {
return true;
}
public static boolean transition72(Test t) {
return t.getAttribute("current").equals("house");
}
public static boolean transition73(Test t) {
return true;
}
public static boolean transition74(Test t) {
return true;
}
public static boolean transition75(Test t) {
return t.getAttribute("current").equals("restaurant");
}
public static boolean transition76(Test t) {
return t.getAttribute("current").equals("bar");
}
}

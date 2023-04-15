package state_diagram.product;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import state_diagram.Diagram;
import state_diagram.elements.Element;
import state_diagram.elements.InitState;

public class FlowController {
	Product current;
	Map<Element,Product>products;
	public FlowController() {
		products = new HashMap<>();
	}
	
	public void step(Map<String, Object>map) {
		try {
			current.execute(map);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Product getCurrent() {
		return current;
	}
	public void setCurrent(Product current) {
		this.current = current;
	}
	
	public static Object invoke(String id, Object... params) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<Functions> clazz = Functions.class;
		
	    Method method = null;
	    for(Method m:clazz.getDeclaredMethods()) {
	    	if(m.getName().equals(id)) {
	    		method = m;
	    	}
	    }

	    return method.invoke(null, params);
	}
	
	public Map<Element, Product> getProducts() {
		return products;
	}

	public static void main(String args[]) {
		Diagram diagram = new Diagram();
		try {
			diagram.load();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FlowController ctrl = new FlowController(); 
		InitState ini = diagram.getInit();
		ctrl.setCurrent(new InitStateProduct(ctrl, ini));
		
		Test test = new Test();
		test.setAttribute("current", "house");
		Map<String, Object>map = new HashMap<>();
		map.put("t", test);
		for(int i=0;i<3;i++) {
			//System.out.println("Exec "+i+" :");
			ctrl.step(map);
		}
		test.setAttribute("current", "bar");
		ctrl.step(map);
		ctrl.step(map);
	}
}

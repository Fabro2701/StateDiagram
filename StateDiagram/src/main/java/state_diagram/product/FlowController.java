package state_diagram.product;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import state_diagram.Diagram;
import state_diagram.elements.InitState;

public class FlowController {
	Product current;
	public FlowController() {
		
	}
	public void step(Object ob) {
		try {
			current.execute();
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
	    		method = clazz.getMethod(id, String.class);
	    	}
	    }

	    return method.invoke(null, params);
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
		InitState ini = (InitState)diagram.getElems().get(1);
		ctrl.setCurrent(new InitStateProduct(ctrl, ini));
		ctrl.step(null);
	}
}

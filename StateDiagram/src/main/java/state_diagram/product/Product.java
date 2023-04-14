package state_diagram.product;

import java.lang.reflect.InvocationTargetException;

public abstract class Product {
	protected FlowController ctrl;
	public Product(FlowController ctrl) {
		this.ctrl = ctrl;
	}
	public abstract Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException ;
}

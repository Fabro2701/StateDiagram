package state_diagram.product;

import java.lang.reflect.InvocationTargetException;

import state_diagram.elements.EndState;

public class EndStateProduct extends Product{

	public EndStateProduct(FlowController ctrl, EndState state) {
		super(ctrl);

	}
	@Override
	public Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return null;
	}
}

package state_diagram.product;

import java.lang.reflect.InvocationTargetException;

import state_diagram.elements.InitState;

public class InitStateProduct extends Product{
	InitState state;
	TransitionProduct t;
	public InitStateProduct(FlowController ctrl, InitState state) {
		super(ctrl);
		this.state = state;
		this.t = new TransitionProduct(ctrl, state.getFromTs().get(0));
	}
	@Override
	public Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			if((Boolean)t.execute()) {
				t.advance();
			}
		return null;
	}
}

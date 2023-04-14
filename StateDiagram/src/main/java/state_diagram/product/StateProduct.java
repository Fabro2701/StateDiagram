package state_diagram.product;

import java.lang.reflect.InvocationTargetException;

import state_diagram.elements.SimpleState;
import state_diagram.elements.Transition;

public class StateProduct extends Product{
	SimpleState state;
	int rest,cont;
	boolean continuous;
	TransitionProduct t;
	public StateProduct(FlowController ctrl, SimpleState state) {
		super(ctrl);
		this.state = state;
		this.rest = state.getRest();
		this.continuous = state.isContinuous();
		this.t = new TransitionProduct(ctrl, state.getFromTs().get(0));
	}
	@Override
	public Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(cont>=rest) {
			FlowController.invoke(state.getId()+"Action"+String.valueOf(state.ID), null);
			cont--;
			if(cont == rest) {
				if((Boolean)t.execute()) {
					t.advance();
				}
			}
		}
		else {
			cont = rest;
			if((Boolean)t.execute()) {
				t.advance();
			}
		}
		return null;
	}
}

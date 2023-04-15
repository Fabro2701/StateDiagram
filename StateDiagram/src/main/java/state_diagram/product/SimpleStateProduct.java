package state_diagram.product;

import java.lang.reflect.InvocationTargetException;

import state_diagram.elements.SimpleState;
import state_diagram.elements.Transition;

public class SimpleStateProduct extends Product{
	SimpleState state;
	int rest,cont;
	boolean continuous;
	TransitionProduct t;
	public SimpleStateProduct(FlowController ctrl, SimpleState state) {
		super(ctrl);
		ctrl.products.put(state, this);
		
		this.state = state;
		this.rest = 0;
		this.continuous = state.isContinuous();
		
		Transition te = state.getFromTs().get(0);
		if(ctrl.products.containsKey(te)) {
			this.t = (TransitionProduct) ctrl.products.get(te);
		}
		this.t = new TransitionProduct(ctrl, state.getFromTs().get(0));
		cont = rest;
	}
	@Override
	public Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//System.out.println("call");
		if(rest==0) {
			action();
			t.execute();
		}
		else {
			if(cont == rest) {
				action();
				cont --;
			}
			else if(cont >= 0) {
				if(continuous)action();
				cont --;
			}
			else {
				if(continuous)action();
				cont = rest;
				t.execute();
			}
		}

		return null;
	}
	public void action() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		FlowController.invoke(state.getId()+"Action"+String.valueOf(state.ID), null);
	}
}

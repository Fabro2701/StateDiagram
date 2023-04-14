package state_diagram.product;

import java.lang.reflect.InvocationTargetException;

import state_diagram.elements.EndState;
import state_diagram.elements.SimpleState;
import state_diagram.elements.Transition;
import state_diagram.elements.TransitionableElement;

public class TransitionProduct extends Product{
	Transition t;
	Product to;
	public TransitionProduct(FlowController ctrl, Transition t) {
		super(ctrl);
		this.t = t;
		TransitionableElement sto = t.getTo();
		if(sto instanceof SimpleState) {
			this.to = new StateProduct(ctrl, (SimpleState) sto);
		}
		if(sto instanceof EndState) {
			this.to = new EndStateProduct(ctrl, (EndState) sto);
		}
	}
	@Override
	public Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		System.out.println("executin transition: "+t.ID);
		return true;
	}
	public void advance() {
		ctrl.setCurrent(to);
	}


}

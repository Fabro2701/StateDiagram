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
		ctrl.products.put(t, this);
		
		this.t = t;
		TransitionableElement sto = t.getTo();
		if(ctrl.products.containsKey(sto)) {
			this.to = ctrl.products.get(sto);
		}
		else {
			if(sto instanceof SimpleState) {
				this.to = new StateProduct(ctrl, (SimpleState) sto);
			}
			if(sto instanceof EndState) {
				this.to = new EndStateProduct(ctrl, (EndState) sto);
			}
		}
		
	}
	@Override
	public Object execute() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		//System.out.println(""+t.ID);
		advance();
		if(ctrl.getCurrent() instanceof InitStateProduct) {
			ctrl.step(t);
		}
		return null;
	}
	public void advance() {
		ctrl.setCurrent(to);
	}


}

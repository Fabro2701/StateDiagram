package state_diagram.translation;

import state_diagram.elements.SimpleState;

public class SimpleStateTranslation {
	private String translate(SimpleState state) {
		StringBuilder sb = new StringBuilder();
		
		String id = state.getId();
		int rest = state.getRest();
		String action = state.getAction();
		boolean continuous = state.isContinuous();
		
		sb.append("private void ").append(id).append("State(){");
		sb.append(action);
		sb.append("}");
		
		return sb.toString();
	}
}

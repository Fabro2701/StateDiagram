package state_diagram.translation;

import java.util.List;

import state_diagram.elements.Element;
import state_diagram.elements.SimpleState;

public class Translation {
	String path, pack;
	public Translation(List<Element>elements, String path, String pack) {
		this.path = path+"/"+pack;
		this.pack = pack;
	}
	public void initState() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("package ").append(pack).append("\n\n");
		sb.append("public abstract class State {");
		sb.append("int rest;");
		sb.append("int cont = 0;");
		sb.append("boolean continuous;");
		sb.append("public State(int rest, boolean continuous){");
		sb.append("this.continuous = continuous;");
		sb.append("this.rest = rest;");
		sb.append("}");
		sb.append("public void execute(){");
		sb.append("if(cont<rest)");
		sb.append("}");
	}
}

package state_diagram.translation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import state_diagram.Diagram;
import state_diagram.elements.Element;
import state_diagram.elements.SimpleState;
import state_diagram.elements.TransitionableElement;

public class Translation {
	String path, pack;
	List<TransitionableElement>elements;
	public Translation(List<TransitionableElement>elements, String path, String pack) throws FileNotFoundException, UnsupportedEncodingException {
		this.path = path;
		this.pack = pack;
		this.elements = elements;
		
		StringBuilder sb = new StringBuilder();
		sb.append(simpleStates());
		
		String code = sb.toString();
		PrintWriter writer = new PrintWriter(path+"Functions.java", "UTF-8");
		writer.println(code);
		writer.close();
	}
	public StringBuilder simpleStates() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("package ").append(pack).append(";\n\n");
		sb.append("public class Functions {\n");
		
		SimpleState se = null;
		for(Element e:elements) {
			if(e instanceof SimpleState) {
				se = (SimpleState)e;
				sb.append("public static void ").append(se.getId()).append("Action").append(se.ID).append("() {\n");
				sb.append(se.getAction());
				sb.append("System.out.println(\"action6\");\n");
				sb.append("}\n");
			}
		}
		
		sb.append("}");
		
		return sb;
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
		try {
			Translation trs = new Translation(diagram.getElems(),"src/main/java/state_diagram/product/","state_diagram.product");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

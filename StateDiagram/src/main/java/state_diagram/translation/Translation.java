package state_diagram.translation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import state_diagram.Diagram;
import state_diagram.elements.Element;
import state_diagram.elements.SimpleState;
import state_diagram.elements.Transition;
import state_diagram.elements.TransitionableElement;
import state_diagram.elements.Transition.TRANSITION_TYPE;

public class Translation {
	String path, pack;
	List<TransitionableElement>elements;
	List<Transition>ts;
	public Translation(List<TransitionableElement>elements, List<Transition>ts, String path, String pack) throws FileNotFoundException, UnsupportedEncodingException {
		this.path = path;
		this.pack = pack;
		this.elements = elements;
		this.ts = ts;
		
		
	}
	public void run() throws FileNotFoundException, UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(pack).append(";\n\n");
		sb.append("public class Functions {\n");
		sb.append(simpleStates());
		sb.append(transitionsStates());
		sb.append("}");
		
		String code = sb.toString();
		PrintWriter writer = new PrintWriter(path+"Functions.java", "UTF-8");
		writer.println(code);
		writer.close();
		
		System.out.println("FINISHED");
	}
	public StringBuilder simpleStates() {
		StringBuilder sb = new StringBuilder();
		SimpleState se = null;
		for(Element e:elements) {
			if(e instanceof SimpleState) {
				se = (SimpleState)e;
				sb.append("public static void ").append(se.getId()).append("Action").append(se.ID).append("() {\n");
				sb.append(se.getAction());
				//sb.append("System.out.println(\"action6\");\n");
				sb.append("\n}\n");
			}
		}
		return sb;
	}
	public StringBuilder transitionsStates() {
		StringBuilder sb = new StringBuilder();
		for(Transition t:ts) {
			sb.append("public static boolean ").append("transition").append(t.ID);
			sb.append("(Test t) {\n");
			if(t.getType()==TRANSITION_TYPE.TRUE)sb.append("return true;");
			else sb.append(t.getCode());
			sb.append("\n}\n");
			
		}
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
			Translation trs = new Translation(diagram.getElems(),diagram.getTransitions(),"src/main/java/state_diagram/product/","state_diagram.product");
			trs.run();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

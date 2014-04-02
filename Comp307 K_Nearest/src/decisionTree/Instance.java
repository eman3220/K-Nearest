package decisionTree;

import java.util.ArrayList;

public class Instance {
	
	private String classType;
	private ArrayList<String> values;
	
	public Instance(String classType, ArrayList<String> values) {
		super();
		this.classType = classType;
		this.values = values;
	}

	public String getClassType() {
		return classType;
	}

	public ArrayList<String> getValues() {
		return values;
	}
}

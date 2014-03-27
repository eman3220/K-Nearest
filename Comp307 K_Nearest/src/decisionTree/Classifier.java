package decisionTree;

import java.util.ArrayList;

public class Classifier {

	private ArrayList<ArrayList<String>> instances;
	private ArrayList<String> classes;
	private ArrayList<String> attributes;

	private Node root;

	public Classifier(ArrayList<ArrayList<String>> ins,
			ArrayList<String> classes, ArrayList<String> attr) {
		this.instances = ins;
		this.classes = classes;
		this.attributes = attr;
	}

	public Node buildTree(ArrayList<ArrayList<String>> instances,
			ArrayList<String> attr) {
		boolean pure = isPure(instances);
		float majority = getMajority(instances);
		if (instances.isEmpty()) {
			return new Node("Leaf - Instances empty",null,null,true,0.5f);
			// return leaf node containing the name and probability of the
			// overall most probable class
		} else if (pure) {
			// return leaf. ...
			return new Node(instances.get(0).get(0),null,null,true,1f);
		} else if (attr.isEmpty()) {
			return new Node("Leaf - Attributes Empty",null,null,true,majority);
			// return something...
		} else {
			// find the best attribute
			String bestAttr = "";
			ArrayList<ArrayList<String>> bestInstsTrue = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> bestInstsFalse = new ArrayList<ArrayList<String>>();
			float bestPurityValue = 0f;
			
			// for each attribute
			for (int i = 0; i < attr.size(); i++) {
				// split into two groups
				ArrayList<ArrayList<String>> groupOne = new ArrayList<ArrayList<String>>();
				ArrayList<ArrayList<String>> groupTwo = new ArrayList<ArrayList<String>>();

				for (ArrayList<String> als : instances) {
					String type = als.get(i + 1);
					// ONLY HANDLES TWO OUTPUTS
					if (type.equals("true")) {
						// add to groupOne
						groupOne.add(als);
					} else if (type.equals("false")) {
						// add to groupTwo
						groupTwo.add(als);
					}
				}
				
				// get purity measure
				float purityValue = 0f;
				purityValue = calcPurity();
				
				if(bestPurityValue>purityValue){
					bestAttr = attr.get(i);
					bestInstsTrue = groupOne;
					bestInstsFalse = groupTwo;
					bestPurityValue = purityValue;
				}
			}
			attr.remove(bestAttr);
			Node left = buildTree(bestInstsTrue,attr);
			Node right = buildTree(bestInstsFalse,attr);
			return new Node(bestAttr, left, right, false, bestPurityValue);
		}
	}

	private float calcPurity() {
		// TODO Auto-generated method stub
		return 0;
	}

	private float getMajority(ArrayList<ArrayList<String>> instances2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean isPure(ArrayList<ArrayList<String>> inst) {
		String previous = "";
		String current = "";
		for(ArrayList<String> als : inst){
			current = als.get(0);
			if(!current.equals(previous) && !previous.equals("")){
				return false;
			}
			previous = als.get(0);
		}
		return true;
	}
}

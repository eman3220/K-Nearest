package decisionTree;

import java.util.HashMap;

public class Node {

	private String name;
	private Node left;
	private Node right;
	private boolean isLeaf;
	private float probability;

	public Node(String Attr, Node left, Node right, boolean isLeaf,
			float probability) {
		this.name = Attr;
		this.left = left;
		this.right = right;
		this.isLeaf = isLeaf;
		this.probability = probability;
	}

	public void printNode(String indent) {
		if (isLeaf) {
			System.out.format("%sClass %s, prob=%4.2f\n", indent, name,
					probability);
		} else {
			System.out.format("%s%s = True : \n", indent, this.name);
			this.left.printNode(indent + "    ");
			System.out.format("%s%s = False : \n", indent, this.name);
			this.right.printNode(indent + "    ");
		}
	}

	public String getName() {
		return name;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public float getProbability() {
		return probability;
	}

	public String classify(Instance inst, HashMap<String,Integer> attributes) {
		if (isLeaf) {
			return name;
		} else {
			int i = attributes.get(name);
			String s = inst.getValues().get(i);
			if(s.equals("true")){
				return left.classify(inst, attributes);
			}else{
				return right.classify(inst, attributes);
			}
		}
	}

}

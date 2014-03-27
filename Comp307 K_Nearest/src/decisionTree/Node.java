package decisionTree;

public class Node {

	private String attr;
	private Node left;
	private Node right;
	private boolean isLeaf;
	private float probability;
	
	public Node(String Attr, Node left, Node right, boolean isLeaf, float probability) {
		this.attr = Attr;
		this.left = left;
		this.right = right;
		this.isLeaf = isLeaf;
		this.probability = probability;
	}
	
}

package Main;

public class IrisClassify {
	
	private Iris iris;
	private int classifiedType;
	
	public IrisClassify(Iris iris, int classifiedType){
		this.iris = iris;
		this.classifiedType = classifiedType;
	}

	public Iris getIris() {
		return iris;
	}

	public int getClassifiedType() {
		return classifiedType;
	}
	
	public String toString(){
		String type = "";
		switch(this.classifiedType){
		case 0:
			type = "Iris-setosa";
			break;
		case 1:
			type = "Iris-versicolor";
			break;
		case 2:
			type = "Iris-virginica";
			break;
		}
		return iris.toString() + " classified as " +type;
	}
}

package nearest;

import java.util.ArrayList;

public class Iris {

	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	
	private ArrayList<Double> data;
	
	private String type;

	public Iris(double sepalLength, double sepalWidth, double petalLength,
			double petalWidth, String type) {
		super();
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.type = type;
		
		this.data = new ArrayList<Double>();
		this.data.add(sepalLength);
		this.data.add(sepalWidth);
		this.data.add(petalLength);
		this.data.add(petalWidth);
	}

	public double getSepalLength() {
		return sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public String getType() {
		return type;
	}
	
	public ArrayList<Double> getData(){
		return this.data;
	}
	
	public String toString(){
		return "Iris("+sepalLength+","+sepalWidth+","+petalLength+","+petalWidth+","+type+")";
	}
}

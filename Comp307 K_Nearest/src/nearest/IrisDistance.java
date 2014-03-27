package nearest;

public class IrisDistance {

	private double distance;
	private Iris iris;

	public IrisDistance(double distance, Iris iris) {
		super();
		this.distance = distance;
		this.iris = iris;
	}

	public double getDistance() {
		return distance;
	}

	public Iris getIris() {
		return iris;
	}

}

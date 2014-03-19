package Main;

import java.util.Comparator;

public class DistanceComparator implements Comparator<IrisDistance> {

	@Override
	public int compare(IrisDistance i1, IrisDistance i2) {
		if(i1.getDistance() > i2.getDistance()){
			return 1;
		}else if(i2.getDistance() > i1.getDistance()){
			return -1;
		}else{
			return 0;
		}
	}

}

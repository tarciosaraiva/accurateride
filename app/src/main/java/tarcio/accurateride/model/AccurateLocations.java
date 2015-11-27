package tarcio.accurateride.model;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccurateLocations implements Serializable {

    private static final long serialVersionUID = -1010121084760290034L;

    private List<AccurateLocation> accurateLocations;

    public AccurateLocations() {
        accurateLocations = new ArrayList<>();
    }

    public void addLocations(List<AccurateLocation> locations) {
        accurateLocations.addAll(locations);
    }

    public int size() {
        return accurateLocations.size();
    }

    public float calculateDistance() {
        float result = 0;
        float[] resultList = new float[1];
        for (int i = 0; i < accurateLocations.size() - 1; i++) {
            Location.distanceBetween(
                    accurateLocations.get(i).getLatitude(),
                    accurateLocations.get(i).getLongitude(),
                    accurateLocations.get(i + 1).getLatitude(),
                    accurateLocations.get(i + 1).getLongitude(),
                    resultList);
            result = result + resultList[0];
        }

        return result;
    }
}

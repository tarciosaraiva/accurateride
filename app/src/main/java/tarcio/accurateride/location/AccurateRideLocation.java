package tarcio.accurateride.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import tarcio.accurateride.activity.MainActivity;

public class AccurateRideLocation implements LocationListener {

    private final String TAG = this.getClass().getSimpleName();

    private MainActivity mainActivity;
    private Location initialLocation;

    public AccurateRideLocation(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Accuracy: ");
//        sb.append(location.getAccuracy());
//        sb.append(" m");
//        sb.append("\n");
//        sb.append("Altitude: ");
//        sb.append(location.getAltitude());
//        sb.append(" m");
//        sb.append("\n");
//        sb.append("Lat: ");
//        sb.append(location.getLatitude());
//        sb.append("\n");
//        sb.append("Lon: ");
//        sb.append(location.getLongitude());
//        sb.append("\n");
//        sb.append("Speed: ");
//        sb.append(location.getSpeed());
//        sb.append(" m/s");

        Log.d(TAG, "Is initialLocation null? " + (initialLocation == null));

        if (initialLocation == null) {
//            initialLocation = new LatLng(location.getLatitude(), location.getLongitude());
            initialLocation = location;
        }

        this.mainActivity.getTxtDistance().setText(String.format("%.2f m", location.distanceTo(initialLocation)));
        this.mainActivity.getTxtCadence().setText("whatever");
        this.mainActivity.getTxtSpeed().setText(String.format("%.2f m/s", location.getSpeed()));
        this.mainActivity.getTxtElapsedTime().setText("whatever");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("AccurateRideLocation", provider + " / " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
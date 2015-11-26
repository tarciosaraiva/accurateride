package tarcio.accurateride.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import tarcio.accurateride.R;
import tarcio.accurateride.component.StatWidget;
import tarcio.accurateride.location.AccurateRideLocation;
import tarcio.accurateride.location.GpsStatusListener;

public class MainActivity extends AppCompatActivity {

    private static final int ACCESS_GPS = 1;

    private StatWidget statDistance;
    private StatWidget statCadence;
    private StatWidget statSpeed;
    private StatWidget statElapsedTime;
    private TextView txtProviderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_GPS);
            return;
        } else {
            requestGpsLocation();
        }

        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        txtProviderStatus = (TextView) findViewById(R.id.txtProviderStatus);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_GPS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestGpsLocation();
        }
    }

    private void requestGpsLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(new GpsStatusListener(this));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new AccurateRideLocation(this));
    }

    public TextView getTxtProviderStatus() {
        return txtProviderStatus;
    }

}

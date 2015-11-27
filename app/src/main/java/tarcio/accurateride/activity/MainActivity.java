package tarcio.accurateride.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.rx.ObservableFactory;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import tarcio.accurateride.R;
import tarcio.accurateride.component.LocationStatWidget;
import tarcio.accurateride.component.StatWidget;
import tarcio.accurateride.io.LocationsData;

public class MainActivity extends AppCompatActivity {

    private static final int ACCESS_GPS = 1;
    private static final String TAG = "MainActivity";

    private StatWidget statDistance;
    private StatWidget statCadence;
    private StatWidget statSpeed;
    private StatWidget statElapsedTime;
    private LocationStatWidget locationStatWidget;

    private boolean isRecording;
    private Subscription timerSubscription;
    private Subscription writerSubscription;

    private LocationsData locationsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Checking GPS permission");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_GPS);
            Log.d(TAG, "No GPS permission");
            return;
        } else {
            Log.d(TAG, "Has GPS permission");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    requestGpsLocation();
                }
            }, "location-thread").start();
        }

        setContentView(R.layout.activity_main);

        initViews();

        locationsData = new LocationsData(getFileStreamPath(LocationsData.DATA_FILE));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "Request Code: " + requestCode);
        if (requestCode == ACCESS_GPS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestGpsLocation();
        }
    }

    private void initViews() {
        locationStatWidget = (LocationStatWidget) findViewById(R.id.locationStatWidget);
        statDistance = (StatWidget) findViewById(R.id.statDistanceWidget);
        statCadence = (StatWidget) findViewById(R.id.statCadenceWidget);
        statSpeed = (StatWidget) findViewById(R.id.statSpeedWidget);
        statElapsedTime = (StatWidget) findViewById(R.id.statTimeWidget);
    }

    private void requestGpsLocation() {
        Log.d(TAG, "Requesting GPS location");
        final SmartLocation smartLocation = SmartLocation.with(this);

        Observable<Location> locationObservable = ObservableFactory.from(smartLocation.location().config(LocationParams.NAVIGATION));
        locationObservable.subscribe(new Action1<Location>() {
            @Override
            public void call(final Location location) {
                final boolean gps = smartLocation.location().state().isGpsAvailable();
                final boolean network = smartLocation.location().state().isNetworkAvailable();

                if (isRecording) {
                    locationsData.addToCache(location);
                }

                if (gps && network) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            locationStatWidget.displayData();
                            locationStatWidget.setStatisticalData(gps, network, location.getAccuracy(), location.getAltitude());
                            setSpeedStat(location.getSpeed());
                            setCadenceStat(location.getSpeed());
                        }
                    });
                }
            }
        });
    }

    private void setCadenceStat(float speed) {
        double wheelCircumference = 700 * Math.PI;
        double gearRatio = 50 * 34;
        double development = (gearRatio * wheelCircumference) / 1000;
        double cadence = speed / development;

        statCadence.setValueText(formatCadence(cadence));
    }

    private String formatCadence(double cadence) {
        return String.format("%d", (int) cadence);
    }

    public void recordActivity(View view) {
        isRecording = true;
        timerSubscription = Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(final Long second) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statElapsedTime.setValueText(formatTime(second));
                    }
                });
            }

            private String formatTime(long seconds) {
                return String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
            }
        });

        Observable<Long> observable = Observable.interval(1, TimeUnit.MINUTES)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                });

        writerSubscription = observable.subscribe(new Action1<Long>() {
            @Override
            public void call(final Long minute) {
                locationsData.persistAndClearCache();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statDistance.setValueText(formatSpeed(locationsData.getDistance()));
                    }
                });
            }

            private String formatSpeed(float distance) {
                return String.format("%.2f", distance);
            }
        });
    }

    public void stopRecording(View view) {
        isRecording = false;
        timerSubscription.unsubscribe();
        writerSubscription.unsubscribe();
    }

    private void setSpeedStat(float speed) {
        statSpeed.setValueText(String.format("%.2f", speed));
    }

}

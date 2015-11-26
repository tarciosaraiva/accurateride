package tarcio.accurateride.location;

import android.location.GpsStatus;
import android.util.Log;

import tarcio.accurateride.R;
import tarcio.accurateride.activity.MainActivity;

public class GpsStatusListener implements GpsStatus.Listener {

    private MainActivity mainActivity;

    public GpsStatusListener(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public void onGpsStatusChanged(int event) {
        Log.d("GpsStatusListener", "Status: " + event);

        switch (event) {
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                // TODO implement timeToFirstFix
                this.mainActivity.getTxtProviderStatus().setText(R.string.gps_locked);
                break;
            case GpsStatus.GPS_EVENT_STARTED:
                this.mainActivity.getTxtProviderStatus().setText(R.string.gps_started);
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                this.mainActivity.getTxtProviderStatus().setText(R.string.gps_stopped);
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                break;
        }
    }

}

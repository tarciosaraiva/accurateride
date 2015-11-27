package tarcio.accurateride.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tarcio.accurateride.R;

public class LocationStatWidget extends RelativeLayout {

    private TextView txtProviderStatus;
    private LinearLayout statLayout;
    private TextView txtGpsStatus;
    private TextView txtNetworkStatus;
    private TextView txtAccuracy;
    private TextView txtAltitude;

    public LocationStatWidget(Context context) {
        super(context);
        init(context, null);
    }

    public LocationStatWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.location_stats, this);

        txtProviderStatus = (TextView) findViewById(R.id.txtProviderStatus);
        statLayout = (LinearLayout) findViewById(R.id.statLayout);

        txtGpsStatus = (TextView) findViewById(R.id.txtGpsStatus);
        txtNetworkStatus = (TextView) findViewById(R.id.txtNetworkStatus);
        txtAccuracy = (TextView) findViewById(R.id.txtAccuracy);
        txtAltitude = (TextView) findViewById(R.id.txtAltitude);
    }

    public void setStatisticalData(final boolean gpsStatus, final boolean networkStatus, final float accuracy, final double altitude) {
        txtGpsStatus.setText(gpsStatus ? getResources().getString(R.string.status_ok) : getResources().getString(R.string.status_nok));
        txtNetworkStatus.setText(networkStatus ? getResources().getString(R.string.status_ok) : getResources().getString(R.string.status_nok));
        txtAccuracy.setText(String.format("%.2f m", accuracy));
        txtAltitude.setText(String.format("%.2f m", altitude));
    }

    public void displayData() {
        txtProviderStatus.setVisibility(GONE);
        statLayout.setVisibility(VISIBLE);
    }
}

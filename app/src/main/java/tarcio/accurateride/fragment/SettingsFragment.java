package tarcio.accurateride.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import tarcio.accurateride.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}

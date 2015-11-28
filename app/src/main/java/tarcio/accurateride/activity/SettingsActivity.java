package tarcio.accurateride.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tarcio.accurateride.fragment.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

}

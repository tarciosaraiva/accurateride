package tarcio.accurateride.preferences;

import android.content.Context;
import android.preference.ListPreference;
import android.preference.Preference;
import android.util.AttributeSet;

public class ListPreferenceWithSummaryValue extends ListPreference {

    public ListPreferenceWithSummaryValue(Context context) {
        super(context);
        init();
    }

    public ListPreferenceWithSummaryValue(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference pref, Object arg1) {
                pref.setSummary(getSummaryWithSetValue());
                return true;
            }
        });
    }

    @Override
    public CharSequence getSummary() {
        return getSummaryWithSetValue();
    }

    private CharSequence getSummaryWithSetValue() {
        return "Current setting is [" + super.getEntry() + "]";
    }
}

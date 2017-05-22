package r1651.weatherapp.ui.preferences;

import android.preference.Preference;

import r1651.weatherapp.ui.abstracts.BaseView;

public interface PreferencesView extends BaseView {

    void setSum(Preference p, Object newValue);
}


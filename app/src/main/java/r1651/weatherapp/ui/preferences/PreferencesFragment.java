package r1651.weatherapp.ui.preferences;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import r1651.weatherapp.R;
import r1651.weatherapp.ui.weather.WeatherPresenter;

public class PreferencesFragment extends PreferenceFragment implements PreferencesView{


    private EditTextPreference chooseTheLocationPref;
    private ListPreference temperatureUnitPref;
    private ListPreference windSpeedUnitPref;
    private PreferencesPresenter mPreferencesPresenter;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        mPreferencesPresenter = new PreferencesPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chooseTheLocationPref = (EditTextPreference) findPreference("chooseTheLocationPref");
        chooseTheLocationPref.setSummary("Current location " + chooseTheLocationPref.getText());
        chooseTheLocationPref.setOnPreferenceChangeListener(
                (p, newValue) -> {
                    mPreferencesPresenter.setSum(p, "location " + newValue);
                    //p.setSummary("Current location " + newValue);
                    return true;
                });

        temperatureUnitPref = (ListPreference) findPreference("temperatureUnitPref");
        temperatureUnitPref.setSummary("Current temperature unit " + temperatureUnitPref.getValue());
        temperatureUnitPref.setOnPreferenceChangeListener(
                (p, newValue) -> {
                    mPreferencesPresenter.setSum(p, "temperature unit: " + newValue);
                    //p.setSummary("Current temperature unit: " + newValue);
                    return true;
                });

        windSpeedUnitPref = (ListPreference) findPreference("windSpeedUnitPref");
        windSpeedUnitPref.setSummary("Current temperature unit " + windSpeedUnitPref.getValue());
        windSpeedUnitPref.setOnPreferenceChangeListener(
                (p, newValue) -> {
                    mPreferencesPresenter.setSum(p, "wind speed unit: " + newValue);
                   // p.setSummary("Current wind speed unit: " + newValue);
                    return true;
                });

        mPreferencesPresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPreferencesPresenter.dropView();
    }

    @Override
    public void setSum(Preference p, Object newValue) {
        p.setSummary("Current " + newValue);
    }
}
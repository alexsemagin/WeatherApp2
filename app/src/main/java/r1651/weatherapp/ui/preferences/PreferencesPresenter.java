package r1651.weatherapp.ui.preferences;

import android.preference.Preference;

import r1651.weatherapp.ui.abstracts.BasePresenter;

public class PreferencesPresenter extends BasePresenter<PreferencesView> {

    @Override
    public void setView(PreferencesView view) {
        super.setView(view);
    }

    @Override
    public void dropView() {
        super.dropView();
    }


    public void setSum(Preference p, Object newValue) {
        mBaseView.setSum(p, newValue);
    }
}

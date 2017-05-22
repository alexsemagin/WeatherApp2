package r1651.weatherapp.ui.weather;

import r1651.weatherapp.ui.abstracts.BasePresenter;

public class WeatherPresenter extends BasePresenter<WeatherView> {

    @Override
    public void setView(WeatherView view) {
        super.setView(view);
    }

    @Override
    public void dropView() {
        super.dropView();
    }

    public void refreshWeather() {
        mBaseView.refreshWeather();
    }

}

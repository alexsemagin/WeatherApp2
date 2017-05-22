package r1651.weatherapp.ui.weather;

import r1651.weatherapp.model.Channel;
import r1651.weatherapp.ui.abstracts.BaseView;

public interface WeatherView extends BaseView {

    void refreshWeather();

    void success(Channel channel);

    void failure(Exception exception);

}

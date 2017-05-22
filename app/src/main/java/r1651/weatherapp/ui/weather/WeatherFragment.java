package r1651.weatherapp.ui.weather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import butterknife.BindView;
import r1651.weatherapp.R;
import r1651.weatherapp.model.Channel;
import r1651.weatherapp.model.Item;
import r1651.weatherapp.service.YahooWeatherService;
import r1651.weatherapp.ui.MainActivity;
import r1651.weatherapp.ui.abstracts.BaseFragment;

public class WeatherFragment extends BaseFragment implements WeatherView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.linear_container)
    LinearLayout linear_container;
    @BindView(R.id.weatherIconImageView)
    ImageView weatherIconImageView;
    @BindView(R.id.conditionTextView)
    TextView conditionTextView;
    @BindView(R.id.locationTextView)
    TextView locationTextView;
    @BindView(R.id.temperatureTextView)
    TextView temperatureTextView;
    @BindView(R.id.windTextView)
    TextView windTextView;
    @BindView(R.id.humidityTextView)
    TextView humidityTextView;
    @BindView(R.id.pressureTextView)
    TextView pressureTextView;
    @BindView(R.id.sunriseTextView)
    TextView sunriseTextView;
    @BindView(R.id.sunsetTextView)
    TextView sunsetTextView;

    private YahooWeatherService mYahooWeatherService;
    private WeatherPresenter mWeatherPresenter;
    private ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherPresenter = new WeatherPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle(getActivity().getTitle());

        MainActivity ma = (MainActivity) this.getActivity();
        Drawer drawer = ma.getDrawer();
        drawer.setToolbar(ma, toolbar, true);

        mYahooWeatherService = new YahooWeatherService(this);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        mWeatherPresenter.setView(this);
        mWeatherPresenter.refreshWeather();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWeatherPresenter.dropView();
    }

    @Override
    public void refreshWeather() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }
        if (!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            dialog.hide();
            Snackbar.make(linear_container, "Connection error", Snackbar.LENGTH_LONG)
                    .show();
        }

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                String city;
                String temperature = getTemperatureFromPref();
                if ((city = getCityFromPref()) != "") {
                    dialog.show();
                    mYahooWeatherService.refreshWeather(city, temperature);
                } else {

                    if (location != null && location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                        mYahooWeatherService.refreshWeather("(" + location.getLatitude() + "," + location.getLongitude() + ")", temperature);
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

    }

    private String getCityFromPref() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getBaseContext());
        return prefs.getString("chooseTheLocationPref", "");
    }

    private String getTemperatureFromPref() {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getBaseContext());
        return prefs.getString("temperatureUnitPref", "c");
    }

    @Override
    public void success(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resource = getResources().getIdentifier("drawable/icon_" + item.getConditions().getCode(), null, getActivity().getPackageName());

        Drawable weatherIconDrawable = ContextCompat.getDrawable(getActivity(), resource);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText("  " + item.getConditions().getTemp() + "\u00B0" + channel.getUnits().getTemperature());

        conditionTextView.setText("  " + item.getConditions().getDescription());

        locationTextView.setText("  " + channel.getTitle());

        windTextView.setText("  " + channel.getWind().getSpeed() + " " + channel.getUnits().getSpeed());

        humidityTextView.setText("  " + channel.getAtmosphere().getHumidity() + "%");

        pressureTextView.setText("  " + channel.getAtmosphere().getPressure() + " " + channel.getUnits().getPressure());

        sunriseTextView.setText("  " + channel.getAstronomy().getSunrise());

        sunsetTextView.setText("  " + channel.getAstronomy().getSunset());
    }

    @Override
    public void failure(Exception exception) {
        dialog.hide();
        Log.d("ALEX", " " + exception.toString());
    }
}

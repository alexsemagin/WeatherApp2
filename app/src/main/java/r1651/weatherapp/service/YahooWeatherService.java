package r1651.weatherapp.service;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import r1651.weatherapp.model.Channel;
import r1651.weatherapp.ui.weather.WeatherView;
import rx.Observable;

public class YahooWeatherService {

    private WeatherView mWeatherView;
    private String location;
    private String temperature;
    private Exception exception;

    public YahooWeatherService(WeatherView weatherView) {
        mWeatherView = weatherView;

    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(String latLong, String temperature) {
        location = latLong;
        this.temperature = temperature;
        // Observable.just(doInBack()).subscribe(s -> onPost(s));

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return doInBack();
            }

            @Override
            protected void onPostExecute(String s) {
                onPost(s);
            }
        }.execute();
    }

    String doInBack() {

        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='%s'", location, temperature);

        String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

        try {
            URL url = new URL(endpoint);

            URLConnection connection = url.openConnection();

            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder result = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    void onPost(String s) {
        if (s == null && exception != null) {
            mWeatherView.failure(exception);
            return;
        }
        try {
            JSONObject data = new JSONObject(s);

            JSONObject queryResults = data.optJSONObject("query");

            if (queryResults.optInt("count") == 0) {
                mWeatherView.failure(new LocationWeatherException("No weather information found for " + location));
                return;
            }

            Channel channel = new Channel();
            channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

            mWeatherView.success(channel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class LocationWeatherException extends Exception {
        public LocationWeatherException(String detail) {
            super(detail);
        }
    }

}

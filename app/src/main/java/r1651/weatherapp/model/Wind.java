package r1651.weatherapp.model;

import org.json.JSONObject;

public class Wind implements JSONPopulator {

    private double speed;

    public int getSpeed() {
        return (int) (speed/1.6);
    }

    @Override
    public void populate(JSONObject data) {
        speed = data.optDouble("speed");
    }
}

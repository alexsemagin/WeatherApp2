package r1651.weatherapp.model;

import org.json.JSONObject;

public class Condition implements JSONPopulator {

    private int code;
    private int temp;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temp = data.optInt("temp");
        description = data.optString("text");
    }
}
package r1651.weatherapp.model;

import org.json.JSONObject;

public class Item implements JSONPopulator {

    private Condition conditions;

    public Condition getConditions() {
        return conditions;
    }

    @Override
    public void populate(JSONObject data) {
        conditions = new Condition();
        conditions.populate(data.optJSONObject("condition"));
    }
}
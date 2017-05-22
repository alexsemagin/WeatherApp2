package r1651.weatherapp.model;

import org.json.JSONObject;

public class Channel implements JSONPopulator {

    private Units units;
    private Item item;
    private Wind wind;
    private Astronomy astronomy;
    private Atmosphere atmosphere;
private String title;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    public Wind getWind() {
        return wind;
    }

    public Astronomy getAstronomy() {
        return astronomy;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));

        astronomy = new Astronomy();
        astronomy.populate(data.optJSONObject("astronomy"));

        atmosphere = new Atmosphere();
        atmosphere.populate(data.optJSONObject("atmosphere"));


        title = data.optString("title");
    }
}
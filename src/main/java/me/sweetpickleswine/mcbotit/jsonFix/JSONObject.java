package me.sweetpickleswine.mcbotit.jsonFix;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

public class JSONObject {
    public JsonObject job;

    public JSONObject() {
        job = new JsonObject();
    }

    public JSONObject(String s) {
        job = JsonHelper.deserialize(s.replaceAll("'", "\""));
    }

    public JSONObject put(String s, Number n) {
        job.addProperty(s, n);
        return this;
    }

    public JSONObject put(String s, String n) {
        job.addProperty(s, n);
        return this;
    }

    public JSONObject put(String s, boolean n) {
        job.addProperty(s, n);
        return this;
    }

    public JSONObject put(String s, JsonElement n) {
        job.add(s, n);
        return this;
    }

    public String getString(String s) {
        return job.get(s).getAsString();
    }

    public int getInt(String s) {
        return job.get(s).getAsInt();
    }

    public double getDouble(String s) {
        return job.get(s).getAsDouble();
    }

    public float getFloat(String s) {
        return job.get(s).getAsFloat();
    }

    public long getLong(String s) {
        return job.get(s).getAsLong();
    }

    public String toString() {
        return job.toString();
    }

    public boolean has(String s) {
        return job.has(s);
    }


}

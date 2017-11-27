package eu.hopu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface Command {
    public JsonArray execute();
}

package ru.itmo.degtiarenko.hw.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class WebResponse {
    private List<String> results;

    public WebResponse(List<String> results) {
        this.results = results;
    }


    public static WebResponse from(BingSearchActor.SearchResults searchResults) {
        final JsonObject jsonResponse = new JsonParser().parse(searchResults.jsonResponse).getAsJsonObject();
        List<String> results = getResultsFromJson(jsonResponse);
        return new WebResponse(results);
    }

    private static List<String> getResultsFromJson(JsonObject json) {
        List<String> results = new ArrayList<>();
        JsonArray array = json.getAsJsonObject("webPages")
                .getAsJsonArray("value");
        for (int i = 0; i < array.size(); i++) {
            JsonObject singleResult = array.get(i).getAsJsonObject();
            String stringResult = singleResult.get("displayUrl").getAsString();
            results.add(stringResult);
        }
        return results;
    }


    public List<String> getResults() {
        return results;
    }
}

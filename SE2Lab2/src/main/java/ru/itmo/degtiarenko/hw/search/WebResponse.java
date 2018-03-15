package ru.itmo.degtiarenko.hw.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WebResponse {
    private final static int MAX_RESULTS = 5;

    private final Source source;
    private final List<String> results;

    public WebResponse(Source source, List<String> results) {
        this.source = source;
        this.results = results.subList(0, MAX_RESULTS);
    }


    public static WebResponse from(BingSearchActor.SearchResults searchResults) {
        final JsonObject jsonResponse = new JsonParser().parse(searchResults.jsonResponse).getAsJsonObject();
        List<String> results = getResultsFromJson(jsonResponse);

        return new WebResponse(Source.BING, results);
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

    public static WebResponse from(Document document) {
        List<String> results = new ArrayList<>();
        Elements elemResults = document.getElementById("links").getElementsByClass("results_links");

        for (Element result : elemResults) {
            Element title = result.getElementsByClass("links_main").first().getElementsByTag("a").first();
            results.add(title.text() + ", " + title.attr("href"));
        }
        return new WebResponse(Source.DUCKDUCKGO, results);
    }

    public List<String> getResults() {
        return results;
    }

    public Source getSource() {
        return source;
    }

    public enum Source {
        BING, DUCKDUCKGO, GOOGLE
    }
}

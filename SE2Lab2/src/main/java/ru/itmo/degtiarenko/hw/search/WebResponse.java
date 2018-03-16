package ru.itmo.degtiarenko.hw.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class WebResponse {
    private final static int MAX_RESULTS = 5;

    private final Source source;
    private final List<String> results;

    public WebResponse(Source source, List<String> results) {
        final int toIndex = MAX_RESULTS < results.size() ? MAX_RESULTS : results.size();

        this.results = results.subList(0, toIndex);
        this.source = source;
    }


    public static WebResponse fromBing(BingSearchActor.SearchResults searchResults) {
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

    public static WebResponse fromDDG(Document document) {
        List<String> results = new ArrayList<>();
        Elements elemResults = document.getElementById("links").getElementsByClass("results_links");

        for (Element result : elemResults) {
            Element title = result.getElementsByClass("links_main").first().getElementsByTag("a").first();
            results.add(title.text() + ", " + title.attr("href"));
        }
        return new WebResponse(Source.DUCKDUCKGO, results);
    }

    public static WebResponse fromGoogle(Document doc) throws UnsupportedEncodingException {
        List<String> results = new ArrayList<>();

        Elements links = doc.select(".g>div>.rc>.r>*");
        for (Element link : links) {
            String title = link.text();
            String url = link.absUrl("href");
            url = URLDecoder.decode(url, "UTF-8");

            if (!url.startsWith("http")) {
                continue; // Ads/news/etc.
            }
            results.add(title + ", " + url);
        }
        return new WebResponse(Source.GOOGLE, results);
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

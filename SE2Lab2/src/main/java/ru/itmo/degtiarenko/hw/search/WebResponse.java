package ru.itmo.degtiarenko.hw.search;

public class WebResponse {
    private final String value;

    public WebResponse(String value) {
        this.value = value;
    }

    public static WebResponse from(BingSearchActor.SearchResults searchResults) {
        String value = searchResults.jsonResponse;
        return new WebResponse(value);
    }

    public String getValue() {
        return value;
    }
}

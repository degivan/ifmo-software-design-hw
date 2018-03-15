package ru.itmo.degtiarenko.hw.search;

import java.util.List;

public class SearchResponse {
    private final List<String> results;

    public SearchResponse(List<String> results) {
        this.results = results;
    }

    public List<String> getResults() {
        return results;
    }
}

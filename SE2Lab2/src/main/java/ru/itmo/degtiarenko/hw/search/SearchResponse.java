package ru.itmo.degtiarenko.hw.search;

import ru.itmo.degtiarenko.hw.search.engines.WebResponse.Source;

import java.util.List;
import java.util.Map;

public class SearchResponse {
    private final Map<Source, List<String>> results;

    public SearchResponse(Map<Source, List<String>> results) {
        this.results = results;
    }

    public Map<Source, List<String>> getResults() {
        return results;
    }
}

package ru.itmo.degtiarenko.hw.search.engines;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DuckDuckGoSearchActor extends SearchEngineActor {
    private final static String DUCKDUCKGO_SEARCH_URL = "https://duckduckgo.com/html/?q=";

    @Override
    protected void processWebRequest(WebRequest request) {
        try {
            Document doc = Jsoup.connect(DUCKDUCKGO_SEARCH_URL + request.getRequest()).get();
            getSender().tell(WebResponse.fromDDG(doc), getSelf());
            context().stop(getSelf());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

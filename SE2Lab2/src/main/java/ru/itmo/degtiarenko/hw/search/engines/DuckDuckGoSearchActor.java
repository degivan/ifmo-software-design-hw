package ru.itmo.degtiarenko.hw.search.engines;

import akka.actor.AbstractActor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DuckDuckGoSearchActor extends AbstractActor {
    private final static String DUCKDUCKGO_SEARCH_URL = "https://duckduckgo.com/html/?q=";

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WebRequest.class, this::processWebRequest)
                .matchAny(this::unhandled)
                .build();
    }

    private void processWebRequest(WebRequest request) {
        try {
            Document doc = Jsoup.connect(DUCKDUCKGO_SEARCH_URL + request.getRequest()).get();
            getSender().tell(WebResponse.fromDDG(doc), getSelf());
            context().stop(getSelf());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ru.itmo.degtiarenko.hw.search;

import akka.actor.AbstractActor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;

public class GoogleSearchActor extends AbstractActor {
    private final static String GOOGLE_URL = "http://www.google.com/search?q=";
    private final static String CHARSET = "UTF-8";


    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WebRequest.class, this::processWebRequest)
                .matchAny(this::unhandled)
                .build();
    }

    private void processWebRequest(WebRequest request) throws IOException {
        final String searchUrl = GOOGLE_URL + URLEncoder.encode(request.getRequest(), CHARSET);
        Document doc = Jsoup.connect(searchUrl).get();
        getSender().tell(WebResponse.fromGoogle(doc), getSelf());
    }
}

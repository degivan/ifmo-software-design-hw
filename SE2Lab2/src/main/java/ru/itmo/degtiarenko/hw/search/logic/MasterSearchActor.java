package ru.itmo.degtiarenko.hw.search.logic;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.itmo.degtiarenko.hw.search.engines.*;
import ru.itmo.degtiarenko.hw.search.engines.WebResponse.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterSearchActor extends AbstractActor {
    private static final String GOOGLE_NAME = "google";
    private static final String BING_NAME = "bing-child";
    private static final String DDG_NAME = "ddg";
    private static final int SOURCE_AMOUNT = 3;
    private static final Map<String, Class<?>> searchClasses = new HashMap<>();
    private final Map<Source, List<String>> results = new HashMap<>();
    private ActorRef returnPoint;

    static {
        searchClasses.put(BING_NAME, BingSearchActor.class);
        searchClasses.put(GOOGLE_NAME, GoogleSearchActor.class);
        searchClasses.put(DDG_NAME, DuckDuckGoSearchActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(StartRequest.class, this::processStartRequest)
                .match(WebResponse.class, this::processWebResponse)
                .matchAny(this::unhandled).build();
    }

    private void processWebResponse(WebResponse webResponse) {
        results.put(webResponse.getSource(), webResponse.getResults());

        if (results.size() == SOURCE_AMOUNT) {
            SearchResponse response = new SearchResponse(results);

            returnPoint.tell(response, getSelf());
            context().stop(self());
        }
    }

    private void processStartRequest(StartRequest startRequest) {
        returnPoint = getSender();

        for (Map.Entry<String, Class<?>> e: searchClasses.entrySet()) {
            String name = e.getKey();
            Class<?> actorClass = e.getValue();
            ActorRef searchChild = context().actorOf(Props.create(actorClass), name);
            searchChild.tell(new WebRequest(startRequest.getRequest()), getSelf());
        }
    }
}

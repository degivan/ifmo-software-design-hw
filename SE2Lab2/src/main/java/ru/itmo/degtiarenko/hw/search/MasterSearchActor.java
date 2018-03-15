package ru.itmo.degtiarenko.hw.search;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import ru.itmo.degtiarenko.hw.search.WebResponse.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterSearchActor extends AbstractActor {
    private static final String BING_NAME = "bing-child";
    private static final String DDG_NAME = "ddg";
    private static final int SOURCE_AMOUNT = 2;

    private final Map<Source, List<String>> results = new HashMap<>();
    private ActorRef returnPoint;

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(StartRequest.class, this::processStartRequest)
                .match(WebResponse.class, this::processWebResponse)
                .matchAny(this::unhandled).build();
    }

    private void processWebResponse(WebResponse webResponse) {
        System.out.println("processWebResponse");

        results.put(webResponse.getSource(), webResponse.getResults());

        if (results.size() == SOURCE_AMOUNT) {
            SearchResponse response = new SearchResponse(results);

            returnPoint.tell(response, getSelf());
            context().stop(self());
        }
    }

    private void processStartRequest(StartRequest startRequest) {
        System.out.println("processStartRequest");
        returnPoint = getSender();

        ActorRef bingChild = context().actorOf(Props.create(BingSearchActor.class), BING_NAME);
        ActorRef ddgChild = context().actorOf(Props.create(DuckDuckGoSearchActor.class), DDG_NAME);
        bingChild.tell(new WebRequest(startRequest.getRequest()), getSelf());
        ddgChild.tell(new WebRequest(startRequest.getRequest()), getSelf());
    }
}

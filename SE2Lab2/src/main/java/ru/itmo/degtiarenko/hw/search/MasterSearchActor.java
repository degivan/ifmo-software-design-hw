package ru.itmo.degtiarenko.hw.search;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.Collections;

public class MasterSearchActor extends AbstractActor {
    private static final String BING_NAME = "bing-child";
    private ActorRef returnPoint;

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(StartRequest.class, this::processStartRequest)
                .match(WebResponse.class, this::processWebResponse)
                .matchAny(this::unhandled).build();
    }

    private void processWebResponse(WebResponse webResponse) {
        System.out.println("processWebResponse");
        SearchResponse response = new SearchResponse(Collections.singletonList(webResponse.getValue()));
        returnPoint.tell(response, getSelf());
    }

    private void processStartRequest(StartRequest startRequest) {
        System.out.println("processStartRequest");
        returnPoint = getSender();
        ActorRef child = context().actorOf(Props.create(BingSearchActor.class), BING_NAME);
        child.tell(new WebRequest(startRequest.getRequest()), getSelf());
    }
}

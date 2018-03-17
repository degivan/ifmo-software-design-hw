package ru.itmo.degtiarenko.hw.search.engines;

import akka.actor.AbstractActor;

public abstract class SearchEngineActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WebRequest.class, this::processWebRequest)
                .matchAny(this::unhandled).build();
    }

    protected abstract void processWebRequest(WebRequest request) throws Exception;
}

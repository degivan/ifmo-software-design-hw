package ru.itmo.degtiarenko.hw.search.logic;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;

import java.util.Scanner;
import java.util.stream.Collectors;

public class SupervisorActor extends AbstractActor {
    private static final String MASTER_NAME = "master";
    private static final String EXIT_LINE = "~exit~";
    private final Scanner in;
    private final Runnable shutdown;

    public SupervisorActor(Runnable shutdown) {
        this.shutdown = shutdown;
        in = new Scanner(System.in);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SearchResponse.class, this::processSearchResponse)
                .match(Terminated.class, this::makeNewRequest)
                .match(StartSupervisorMsg.class, this::makeNewRequest)
                .matchAny(this::unhandled).build();
    }

    private void makeNewRequest(Object msg) {
        String request = in.nextLine();
        if (request.equals(EXIT_LINE)) {
            context().stop(getSelf());
        } else {
            ActorRef master = context().actorOf(Props.create(MasterSearchActor.class), MASTER_NAME);
            context().watch(master);

            master.tell(new StartRequest(request), getSelf());
        }
    }

    private void processSearchResponse(SearchResponse searchResponse) {
        System.out.println(getPrintable(searchResponse));
    }

    private static String getPrintable(SearchResponse response) {
        return response.getResults()
                .entrySet()
                .stream()
                .flatMap(e -> e.getValue().stream().map(res -> e.getKey() + " --> " + res))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        in.close();
        shutdown.run();
    }
}

package ru.itmo.degtiarenko.hw.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SearchAggregator {
    private static final String SYSTEM_NAME = "system";
    private static final String SUPERVISOR_NAME = "supervisor";

    public static void main(String[] args) throws Exception {
        ActorSystem actors = ActorSystem.create(SYSTEM_NAME);
        Runnable shutdown = actors::terminate;
        ActorRef supervisor = actors.actorOf(Props.create(SupervisorActor.class, shutdown), SUPERVISOR_NAME);

        supervisor.tell(new StartSupervisorMsg(), ActorRef.noSender());

    }
}

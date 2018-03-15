package ru.itmo.degtiarenko.hw.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SearchAggregator {
    private static final String EXIT_LINE = "~exit~";
    private static final String SYSTEM_NAME = "system";
    private static final String MASTER_NAME = "master";
    private static final Timeout TIMEOUT = Timeout.apply(60, TimeUnit.SECONDS);

    private static ActorSystem actors;

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        actors = ActorSystem.create(SYSTEM_NAME);
        while (true) {
            String searchRequest = in.nextLine();
            if (searchRequest.equals(EXIT_LINE)) {
                in.close();
                break;
            } else {
                handleRequest(searchRequest);
            }
        }
    }

    private static void handleRequest(String searchRequest) throws Exception {
        ActorRef master = actors.actorOf(Props.create(MasterSearchActor.class), MASTER_NAME);
        Future<Object> future = Patterns.ask(master, new StartRequest(searchRequest), TIMEOUT);

        SearchResponse response = (SearchResponse)Await.result(future, TIMEOUT.duration());

        System.out.print(getPrintable(response));
    }

    private static String getPrintable(SearchResponse response) {
        return response.getResults()
                .stream()
                .collect(Collectors.joining("\n"));
    }
}

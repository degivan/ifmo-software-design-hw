package ru.itmo.degtiarenko.hw.search.engines;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class BingSearchActorTest {
    private static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system, Duration.apply(70, TimeUnit.SECONDS), true);
        system = null;
    }

    @Test
    public void testSearch() {
        new TestKit(system) {{
            ActorRef actorRef = system.actorOf(Props.create(BingSearchActor.class), "bing");
            actorRef.tell(new WebRequest("test"), testActor());
            expectMsgClass(Duration.apply(30,TimeUnit.SECONDS), WebResponse.class);
        }};
    }
}

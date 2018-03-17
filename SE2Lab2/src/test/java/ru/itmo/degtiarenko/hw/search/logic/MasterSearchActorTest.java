package ru.itmo.degtiarenko.hw.search.logic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.itmo.degtiarenko.hw.search.logic.MasterSearchActor;
import ru.itmo.degtiarenko.hw.search.logic.SearchResponse;
import ru.itmo.degtiarenko.hw.search.logic.StartRequest;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class MasterSearchActorTest {
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
    public void testMaster() {
        new TestKit(system) {{
            ActorRef actorRef = system.actorOf(Props.create(MasterSearchActor.class), "master");
            actorRef.tell(new StartRequest("test"), testActor());
            SearchResponse response = expectMsgClass(Duration.apply(30,TimeUnit.SECONDS), SearchResponse.class);
            assertTrue(response.getResults().size() == 3);
        }};
    }
}

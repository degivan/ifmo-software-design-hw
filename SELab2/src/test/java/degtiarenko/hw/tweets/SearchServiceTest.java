package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xebialabs.restito.server.StubServer;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.not;
import static com.xebialabs.restito.semantics.Condition.parameter;
import static org.junit.Assert.assertEquals;

public class SearchServiceTest {
    private static final int PORT = 20362;
    private StubServer stubServer;

    @Before
    public void startStubServer() {
        stubServer = new StubServer(PORT);
        stubServer.start();
    }

    @Test
    public void testGetTweetsWithHashTag() throws ParseException, UnirestException {
        Tweet tweet1 = new Tweet(new DateTime().minusHours(2), "30");
        whenHttp(stubServer).match(not(parameter("max_id", "30")))
                .then(stringContent(TestUtils.convertTweetsToString(tweet1)));
        whenHttp(stubServer).match(parameter("max_id", "30"))
                .then(stringContent(TestUtils.convertTweetsToString()));
        SearchService searchService = new SearchServiceImpl("Test token", "http://localhost:" + PORT);

        List<Tweet> result = searchService.getTweetsWithHashTag("hey", new DateTime().minusHours(15));
        assertEquals(Collections.singletonList(tweet1), result);
    }
}

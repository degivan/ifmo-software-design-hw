package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.alwaysTrue;
import static com.xebialabs.restito.semantics.Condition.not;
import static com.xebialabs.restito.semantics.Condition.parameter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SearchServiceTest {
    private static final int PORT = 20362;
    private static final String SEARCH_URL = "http://localhost:" + PORT;
    private StubServer stubServer;

    @Before
    public void startStubServer() {
        stubServer = new StubServer(PORT);
        stubServer.start();
    }

    @After
    public void stopStubServer() {
        stubServer.stop();
    }

    @Test
    public void testGetTweetsWithHashTag() throws ParseException, UnirestException {
        Tweet tweet1 = new Tweet(new DateTime().minusHours(2), "", "30");
        whenHttp(stubServer).match(not(parameter("max_id", "30")))
                .then(stringContent(TestUtils.convertTweetsToString(tweet1)));
        whenHttp(stubServer).match(parameter("max_id", "30"))
                .then(stringContent(TestUtils.convertTweetsToString()));
        SearchService searchService = new SearchServiceImpl("Test token", SEARCH_URL);

        List<Tweet> result = searchService.getTweetsWithHashTag("hey", new DateTime().minusHours(15));
        assertEquals(Collections.singletonList(tweet1), result);
    }

    @Test
    public void testGetTweetsWithHashTag_noConnection() throws ParseException, UnirestException {
        try {
            whenHttp(stubServer).match(alwaysTrue())
                    .then(status(HttpStatus.NOT_FOUND_404));
            SearchService searchService = new SearchServiceImpl("Test token", SEARCH_URL);
            searchService.getTweetsWithHashTag("hey", new DateTime().minusHours(3));
            fail();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}

package degtiarenko.hw.tweets;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class TweetStatisticServiceImpl implements TweetStatisticService {

    private static final String AUTH_PREFIX = "Bearer ";
    private static final String HASHTAG_PREFIX = "%23";
    private static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json";

    private final String authValue;

    public static TweetStatisticServiceImpl create(String pathToProperties) throws IOException {
        Properties prop = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream(pathToProperties);
        prop.load(input);

        return new TweetStatisticServiceImpl(prop.getProperty("bearer_token"));
    }

    private TweetStatisticServiceImpl(String bearerToken) {
        this.authValue = AUTH_PREFIX + bearerToken;
    }

    @Override
    public List<Integer> getHashTagPopularity(String hashTag, int hours) throws UnirestException {
        return Collections.singletonList(todayTweetsWithHashTag(hashTag).length());
    }

    //actually gets 100 recent tweets now
    private JSONArray todayTweetsWithHashTag(String hashTag) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(SEARCH_URL)
                .queryString("q", HASHTAG_PREFIX + hashTag)
                .queryString("result_type", "recent")
                .queryString("count", "100")
                .header("Authorization", authValue)
                .asJson();
        return jsonResponse.getBody().getObject().getJSONArray("statuses");
    }
}

package degtiarenko.hw.tweets;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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
    public List<Long> getHashTagPopularity(String hashTag, int hours) throws UnirestException, ParseException {
        DateTime lastTime = new DateTime().minusHours(hours);
        Map<Integer, Long> tweetsPerHour = getTweetsWithHashTag(hashTag).stream()
                .filter(tweet -> tweet.getDate().isAfter(lastTime))
                .collect(Collectors.groupingBy(tweet -> Hours.hoursBetween(tweet.getDate(), lastTime).getHours(), Collectors.counting()));
        return tweetsPerHour.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    //actually gets 100 recent tweets now
    private List<Tweet> getTweetsWithHashTag(String hashTag) throws UnirestException, ParseException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(SEARCH_URL)
                .queryString("q", HASHTAG_PREFIX + hashTag)
                .queryString("result_type", "recent")
                .queryString("count", "100")
                .header("Authorization", authValue)
                .asJson();
        return new TweetParser().getTweetsFromResponse(jsonResponse);
    }
}

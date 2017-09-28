package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import static java.util.stream.Collectors.*;

public class TweetStatisticServiceImpl implements TweetStatisticService {

    private static final String AUTH_PREFIX = "Bearer ";

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
        Map<Integer, Long> tweetsPerHour = getTweetsWithHashTag(hashTag, lastTime).stream()
                .filter(tweet -> tweet.getDate().isAfter(lastTime))
                .collect(groupingBy(tweet -> Hours.hoursBetween(tweet.getDate(), lastTime).getHours(), counting()));
        return tweetsPerHour.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    //actually gets 100 recent tweets now
    private List<Tweet> getTweetsWithHashTag(String hashTag, DateTime since) throws UnirestException, ParseException {
        DateTime lastTweetTime = new DateTime();
        TweetWalker tweetWalker = new TweetWalker(hashTag, authValue);
        List<Tweet> tweets = new ArrayList<>();
        while (lastTweetTime.isAfter(since)) {
            List<Tweet> newTweets = tweetWalker.getNextTweets();
            lastTweetTime = newTweets.get(newTweets.size() - 1).getDate();
            tweets.addAll(newTweets);
        }
        return tweets;
    }
}

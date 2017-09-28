package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TweetSearchServiceImpl implements TweetSearchService {
    private static final String AUTH_PREFIX = "Bearer ";
    private final String authValue;

    public TweetSearchServiceImpl(String bearerToken) {
        this.authValue = AUTH_PREFIX + bearerToken;
    }

    @Override
    public List<Tweet> getTweetsWithHashTag(String hashTag, DateTime since) throws ParseException, UnirestException {
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

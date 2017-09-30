package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements SearchService {
    private static final String AUTH_PREFIX = "Bearer ";
    private final String authValue;
    private final String searchUrl;

    public SearchServiceImpl(String bearerToken, String searchUrl) {
        this.authValue = AUTH_PREFIX + bearerToken;
        this.searchUrl = searchUrl;
    }

    @Override
    public List<Tweet> getTweetsWithHashTag(String hashTag, DateTime since) throws ParseException, UnirestException {
        DateTime lastTweetTime = new DateTime();
        TweetWalker tweetWalker = new TweetWalker(hashTag, authValue, searchUrl);
        List<Tweet> tweets = new ArrayList<>();
        while (lastTweetTime.isAfter(since)) {
            List<Tweet> newTweets = tweetWalker.getNextTweets();
            if (newTweets.isEmpty()) {
                break;
            }
            lastTweetTime = newTweets.get(newTweets.size() - 1).getDate();
            tweets.addAll(newTweets);
        }
        return tweets;
    }
}

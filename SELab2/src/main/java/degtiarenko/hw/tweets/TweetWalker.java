package degtiarenko.hw.tweets;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import java.text.ParseException;
import java.util.List;

public class TweetWalker {
    private static final String HASHTAG_PREFIX = "%23";
    private static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json";
    private final String hashTag;
    private final String authValue;
    private String lastId = "";
    private final TweetParser parser;

    public TweetWalker(String hashTag, String authValue) {
        this.hashTag = HASHTAG_PREFIX + hashTag;
        this.authValue = authValue;
        this.parser = new TweetParser();
    }

    public List<Tweet> getNextTweets() throws UnirestException, ParseException {
        HttpRequest base = Unirest.get(SEARCH_URL)
                .queryString("q", HASHTAG_PREFIX + hashTag)
                .queryString("result_type", "recent")
                .queryString("count", "100")
                .header("Authorization", authValue);
        if (!lastId.equals("")) {
            base = base.queryString("max_id", lastId);
        }
        List<Tweet> tweets = parser.getTweetsFromResponse(base.asJson());
        lastId = tweets.get(tweets.size() - 1).getId();
        return tweets;
    }
}

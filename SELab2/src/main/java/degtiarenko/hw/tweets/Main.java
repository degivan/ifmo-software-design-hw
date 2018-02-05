package degtiarenko.hw.tweets;

import org.joda.time.DateTime;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream("twitter_api.properties");
        prop.load(input);

        SearchService searchService = new SearchServiceImpl(prop.getProperty("bearer_token"), prop.getProperty("search_url"));
        List<Tweet> angryTweets = searchService.getTweetsWithHashTag("angry", new DateTime().minusDays(5));

        for(Tweet tweet: angryTweets) {
            System.out.println(tweet);
        }
    }
}

package degtiarenko.hw.tweets;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream("twitter_api.properties");
        prop.load(input);

        TweetSearchService tweetSearchService = new TweetSearchServiceImpl(prop.getProperty("bearer_token"));
        TweetStatisticService tweetStatisticService = new TweetStatisticServiceImpl(tweetSearchService);

        out.println(tweetStatisticService.getHashTagPopularity("Ваня", 24));
        out.println(tweetStatisticService.getHashTagPopularity("Ксюша", 24));
        out.println(tweetStatisticService.getHashTagPopularity("grey", 24));
        out.println(tweetStatisticService.getHashTagPopularity("feminist", 24));
    }
}

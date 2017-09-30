package degtiarenko.hw.tweets;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.out;

public class Main {

    public static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json";

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream("twitter_api.properties");
        prop.load(input);

        SearchService searchService = new SearchServiceImpl(prop.getProperty("bearer_token"), SEARCH_URL);
        StatisticService statisticService = new StatisticServiceImpl(searchService);

        out.println(statisticService.getHashTagPopularity("Yandex", 2));
        out.println(statisticService.getHashTagPopularity("Yahoo", 5));
    }
}

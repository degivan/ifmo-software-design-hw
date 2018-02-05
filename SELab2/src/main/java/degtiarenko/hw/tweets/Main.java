package degtiarenko.hw.tweets;

import org.joda.time.DateTime;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {

    private static final DateTime FIFTY_DAYS_AGO = new DateTime().minusDays(50);

    private static final Map<Integer, String> ordinalClasses = new HashMap<>();

    static {
        ordinalClasses.put(0, "0: no anger can be inferred");
        ordinalClasses.put(1, "1: low amount of anger can be inferred");
        ordinalClasses.put(2, "2: moderate amount of anger can be inferred");
        ordinalClasses.put(3, "3: high amount of anger can be inferred");
    }

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream input = ClassLoader.getSystemResourceAsStream("twitter_api.properties");
        prop.load(input);

        SearchService searchService = new SearchServiceImpl(prop.getProperty("bearer_token"), prop.getProperty("search_url"));
        List<Tweet> tweets = searchService.getTweetsWithHashTag("angry", FIFTY_DAYS_AGO);
        tweets.addAll(searchService.getTweetsWithHashTag("frustrated", FIFTY_DAYS_AGO));
        tweets.addAll(searchService.getTweetsWithHashTag("pissedoff", FIFTY_DAYS_AGO));
        tweets.addAll(searchService.getTweetsWithHashTag("annoying", FIFTY_DAYS_AGO));
        tweets.addAll(searchService.getTweetsWithHashTag("upset", FIFTY_DAYS_AGO));
        tweets.addAll(searchService.getTweetsWithHashTag("annoyed", FIFTY_DAYS_AGO));
        tweets = tweets.stream()
                .filter(tw -> !tw.getText().contains("RT"))
                .filter(tw -> !tw.getText().contains("http"))
                .collect(Collectors.toList());
        Map<String, Integer> tweetsIntensity = new HashMap<>();
        guessIntensity(tweets, tweetsIntensity);
        writeToFile(tweets, tweetsIntensity, "new_tweets.txt");
    }

    private static void guessIntensity(List<Tweet> tweets, Map<String, Integer> tweetsIntensity) {
        for(Tweet tweet: tweets) {
            String text = tweet.getText();
            String id = tweet.getId();
            if(text.contains("#annoyed")){
                tweetsIntensity.put(id, 1);
            }
            if(text.contains("#annoying")){
                tweetsIntensity.put(id, 1);
            }
            if(text.contains("#upset")){
                tweetsIntensity.put(id, 2);
            }
            if(text.contains("#pissedoff")){
                tweetsIntensity.put(id, 3);
            }
            if(text.contains("#frustrated")){
                tweetsIntensity.put(id, 3);
            }

            if(text.contains("#angry")){
                tweetsIntensity.put(id, 3);
            }
        }
    }

    private static void writeToFile(List<Tweet> angryTweets, Map<String, Integer> tweetsIntensity,
                                    String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        for (Tweet tweet: angryTweets) {
            Integer intensity = tweetsIntensity.getOrDefault(tweet.getId(), 1);
            String tweetString = new StringBuilder().append(tweet.getId())
                    .append('\t')
                    .append(tweet.getText().replace('\n', ' '))
                    .append('\t')
                    .append("anger")
                    .append('\t')
                    .append(ordinalClasses.get(intensity))
                    .append('\n')
                    .toString();
            fw.write(tweetString);
        }
        fw.close();
    }
}

package degtiarenko.hw.tweets;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws Exception {
        TweetStatisticService tweetService = TweetStatisticServiceImpl.create("twitter_api.properties");

        out.println(tweetService.getHashTagPopularity("Ваня", 24));
        out.println(tweetService.getHashTagPopularity("Ксюша", 24));
        out.println(tweetService.getHashTagPopularity("grey", 24));
    }
}

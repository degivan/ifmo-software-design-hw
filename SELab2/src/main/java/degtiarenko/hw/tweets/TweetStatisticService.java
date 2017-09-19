package degtiarenko.hw.tweets;

import java.util.List;

public interface TweetStatisticService {
    List<Integer> getHashtagPopularity(String hashtag, int hours);
}

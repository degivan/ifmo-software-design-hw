package degtiarenko.hw.tweets;

import java.util.List;

public interface TweetStatisticService {
    List<Integer> getHashTagPopularity(String hashTag, int hours) throws Exception;
}

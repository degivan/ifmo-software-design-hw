package degtiarenko.hw.tweets;

import java.util.List;

public interface StatisticService {
    List<Long> getHashTagPopularity(String hashTag, int hours) throws Exception;
}

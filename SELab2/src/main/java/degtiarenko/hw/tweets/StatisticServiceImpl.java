package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class StatisticServiceImpl implements StatisticService {

    private final SearchService searchService;

    public StatisticServiceImpl(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<Long> getHashTagPopularity(String hashTag, int hours) throws UnirestException, ParseException {
        if (hours < 0 || hours > 24) {
            throw new IllegalArgumentException("Hours should be in range between 0 and 24");
        }
        List<Long> result = new ArrayList<>();
        DateTime currentTime = new DateTime();
        DateTime lastTime = currentTime.minusHours(hours);
        Map<Integer, Long> tweetsPerHour = searchService.getTweetsWithHashTag(hashTag, lastTime).stream()
                .filter(tweet -> tweet.getDate().isAfter(lastTime))
                .collect(groupingBy(tweet -> Hours.hoursBetween(tweet.getDate(), currentTime).getHours(), counting()));
        for (int i = 0; i < hours; i++) {
            result.add(tweetsPerHour.getOrDefault(i, 0L));
        }
        return result;
    }
}

package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class StatisticServiceImpl implements StatisticService {

    private final SearchService searchService;

    public StatisticServiceImpl(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<Long> getHashTagPopularity(String hashTag, int hours) throws UnirestException, ParseException {
        DateTime lastTime = new DateTime().minusHours(hours);
        Map<Integer, Long> tweetsPerHour = searchService.getTweetsWithHashTag(hashTag, lastTime).stream()
                .filter(tweet -> tweet.getDate().isAfter(lastTime))
                .collect(groupingBy(tweet -> Hours.hoursBetween(tweet.getDate(), lastTime).getHours(), counting()));
        return tweetsPerHour.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(toList());
    }
}

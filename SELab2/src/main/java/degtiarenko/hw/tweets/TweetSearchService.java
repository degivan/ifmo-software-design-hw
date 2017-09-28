package degtiarenko.hw.tweets;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.util.List;

public interface TweetSearchService {
    List<Tweet> getTweetsWithHashTag(String hashTag, DateTime since) throws ParseException, UnirestException;
}

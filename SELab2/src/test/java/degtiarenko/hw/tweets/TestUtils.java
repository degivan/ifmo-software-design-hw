package degtiarenko.hw.tweets;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class TestUtils {
    private static final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("EEE MMM d hh:mm:ss Z yyyy")
            .withLocale(Locale.ENGLISH);

    public static String convertTweetsToString(Tweet... tweets) {
        JSONObject jsonObject = new JSONObject();
        JSONArray statuses = new JSONArray();
        for(Tweet tweet: tweets) {
            statuses.put(tweetToJson(tweet));
        }
        jsonObject.put("statuses", statuses);
        return jsonObject.toString();
    }

    private static JSONObject tweetToJson(Tweet tweet) {
        return new JSONObject().put("id_str", tweet.getId())
                .put("created_at", tweet.getDate().toString(dateFormat));
    }
}

package degtiarenko.hw.tweets;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TweetList {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d hh:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
    private final JSONArray tweets;

    public TweetList(JSONArray tweets) {
        this.tweets = tweets;
    }

    public int getListLength() {
        return tweets.length();
    }

    public Date getTweetDate(int index) throws ParseException {
        String date = tweets.getJSONObject(index).getString("created_at");
        return dateFormat.parse(date);
    }

    public String getId(int index) {
        return tweets.getJSONObject(index).getString("id");
    }
}

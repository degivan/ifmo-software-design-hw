package degtiarenko.hw.tweets;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TweetParser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d hh:mm:ss ZZZZZ yyyy", Locale.ENGLISH);

    public TweetParser() { }

    public List<Tweet> getTweetsFromResponse(HttpResponse<JsonNode> response) throws ParseException {
        List<Tweet> result = new ArrayList<>();
        JSONArray jsonTweets = response.getBody().getObject().getJSONArray("statuses");
        for (int i = 0; i < jsonTweets.length(); i++) {
            result.add(parseJSONTweet(jsonTweets.getJSONObject(i)));
        }
        return result;
    }

    private Tweet parseJSONTweet(JSONObject jsonObject) throws ParseException {
        Date date = dateFormat.parse(jsonObject.getString("created_at"));
        String id = jsonObject.getString("id_str");
        String text = jsonObject.getString("text");

        return new Tweet(new DateTime(date), text, id);
    }
}

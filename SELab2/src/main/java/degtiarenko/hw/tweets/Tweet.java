package degtiarenko.hw.tweets;


import org.joda.time.DateTime;

public class Tweet {
    private final DateTime date;
    private final String id;

    public Tweet(DateTime date, String id) {
        this.date = date;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public DateTime getDate() {
        return date;
    }
}

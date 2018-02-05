package degtiarenko.hw.tweets;


import org.joda.time.DateTime;

public class Tweet {
    private final DateTime date;
    private final String text;
    private final String id;

    public Tweet(DateTime date, String text, String id) {
        this.date = date;
        this.text = text;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public DateTime getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        return id != null ? id.equals(tweet.id) : tweet.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "date=" + date +
                ", text='" + text + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

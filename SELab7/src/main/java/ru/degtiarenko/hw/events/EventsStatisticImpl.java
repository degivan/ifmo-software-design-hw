package ru.degtiarenko.hw.events;

import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {
    private static final int MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = MINUTES_IN_HOUR * 60;
    private final Map<String, List<Instant>> events = new ConcurrentHashMap<>();
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        Instant now = clock.instant();
        events.putIfAbsent(name, new ArrayList<>());
        events.get(name).add(now);
    }

    @Override
    public int getEventStatisticByName(String name) {
        updateAllStatistic();
        return countStatistic(events.get(name));
    }

    private void updateAllStatistic() {
        Instant hourAgo = clock.instant().minusSeconds(SECONDS_IN_HOUR);
        events.values().forEach(list -> list.removeIf(timestamp -> timestamp.isBefore(hourAgo)));
        clearEmptyMapEntries();
    }

    private void clearEmptyMapEntries() {
        events.keySet().stream()
                .filter(name -> events.get(name).isEmpty())
                .forEach(events::remove);
    }

    @Override
    public int getAllEventStatistic() {
        updateAllStatistic();
        return countStatistic(events.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
    }

    @Override
    public void printStatistic() {
        printStatisticByName("Common", getAllEventStatistic());
        events.keySet()
                .forEach(name -> printStatisticByName(name, getEventStatisticByName(name)));
    }

    private void printStatisticByName(String name, int rpm) {
        System.out.println(String.format("%s RPM: %d", name, rpm));
    }

    private int countStatistic(List<Instant> timestamps) {
        return (int) Math.ceil(timestamps.size() / (double) MINUTES_IN_HOUR);
    }
}
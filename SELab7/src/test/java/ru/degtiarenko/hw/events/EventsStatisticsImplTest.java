package ru.degtiarenko.hw.events;


import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class EventsStatisticsImplTest {

    private static final TestClock TEST_CLOCK = new TestClock(1);

    @Test
    public void testGetEventStatisticByName() {
        EventsStatistic statistic = createStatistic(() -> TEST_CLOCK);
        String name = "Champagne bottle opened";
        for (int i = 0; i < 6000; i++) {
            statistic.incEvent(name);
        }
        assertEquals(statistic.getEventStatisticByName(name), 60);
    }

    @Test
    public void testGetAllStatistic() {
        EventsStatistic statistic = createStatistic(() -> TEST_CLOCK);
        String event1 = "SD Lab accepted";
        String event2 = "New challenger arrived";
        for (int i = 0; i < 7000; i++) {
            statistic.incEvent(i % 2 == 0 ? event1 : event2);
        }
        assertEquals(statistic.getAllEventStatistic(), 60);
        assertEquals(statistic.getEventStatisticByName(event1), 30);
        assertEquals(statistic.getEventStatisticByName(event2), 30);
    }

    @Test
    public void testZeroAfterHour() {
        EventsStatistic statistic = createStatistic(() -> TEST_CLOCK);
        for (int i = 0; i < 7000; i++) {
            statistic.incEvent("Uber driver arrived");
        }
        for (int i = 0; i < 3601; i++) {
            TEST_CLOCK.instant();
        }
        assertEquals(statistic.getAllEventStatistic(), 0);
    }

    @Test
    public void testGetAllStatisticEveryMinuteEvent() {
        EventsStatistic statistic = createStatistic(() -> new TestClock(30));
        for (int i = 0; i < 7000; i++) {
            statistic.incEvent("Uber driver arrived");
        }
        assertEquals(statistic.getAllEventStatistic(), 2);
    }

    private EventsStatistic createStatistic(Supplier<Clock> clockSupplier) {
        return new EventsStatisticImpl(clockSupplier.get());
    }

    private static class TestClock extends Clock {
        private final int plus;
        private Instant instant = Instant.now();

        TestClock(int plus) {
            super();
            this.plus = plus;
        }

        @Override
        public ZoneId getZone() {
            return ZoneId.systemDefault();
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return null;
        }

        @Override
        public Instant instant() {
            instant = instant.plusSeconds(plus);
            return instant;
        }
    }
}

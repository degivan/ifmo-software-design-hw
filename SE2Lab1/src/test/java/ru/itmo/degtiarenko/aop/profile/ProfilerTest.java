package ru.itmo.degtiarenko.aop.profile;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ProfilerTest {
    @Test
    public void testSetPackageName() {
        String test = "test";
        Profiler.setPackageName(test);
        assertEquals(Profiler.getPackageName(), test);
    }

    @Test
    public void testMethodCalled() {
        Profiler profiler = Profiler.getInstance();
        PrintStream mock = Mockito.mock(PrintStream.class);

        profiler.methodCalled("test", 1_000_000L);
        profiler.methodCalled("test", 2_000_000L);
        profiler.methodCalled("test", 3_000_000L);
        profiler.methodCalled("test", 4_000_000L);
        profiler.printStats(mock);
        Mockito.verify(mock).println("test:  4, 2.5, 4");
    }
}

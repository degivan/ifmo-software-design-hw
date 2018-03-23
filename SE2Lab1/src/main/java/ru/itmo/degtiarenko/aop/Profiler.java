package ru.itmo.degtiarenko.aop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Profiler {
    private static final Profiler PROFILER = new Profiler();
    private final Map<String, Stats> methodStatistics = new HashMap<>();

    private Profiler() {
    }

    public static Profiler getInstance() {
        return PROFILER;
    }

    public void methodCalled(String methodName, long execTime) {
        methodStatistics.putIfAbsent(methodName, new Stats());
        Stats stats = methodStatistics.get(methodName);
        stats.add(execTime);
    }

    public void printStats() {
        for (Map.Entry<String, Stats> kv : methodStatistics.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(kv.getKey());
            sb.append(":  ");
            sb.append(kv.getValue().count());
            sb.append(", ");
            sb.append(kv.getValue().avg());
            sb.append(", ");
            sb.append(kv.getValue().max());
            System.out.println(sb.toString());
        }
    }

    private static class Stats {
        private static final int NANO_TO_MILLI_FACTOR = 1_000_000;
        Set<Long> executionTimes = new HashSet<>();

        void add(long execTime) {
            executionTimes.add(execTime);
        }

        int count() {
            return executionTimes.size();
        }

        double avg() {
            return executionTimes.stream()
                    .mapToLong(v -> v)
                    .average().getAsDouble() / NANO_TO_MILLI_FACTOR;
        }

        long max() {
            return executionTimes.stream()
                    .mapToLong(v -> v)
                    .max().getAsLong() / NANO_TO_MILLI_FACTOR;
        }
    }
}

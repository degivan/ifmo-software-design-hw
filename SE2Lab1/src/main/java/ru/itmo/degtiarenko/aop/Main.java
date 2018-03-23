package ru.itmo.degtiarenko.aop;

public class Main {
    public static void main(String[] args) {
        Double d = 4.0;

        new Factorial().factorial(d.intValue());

        Profiler.getInstance().printStats();
    }
}

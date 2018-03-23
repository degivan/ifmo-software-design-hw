package ru.itmo.degtiarenko.aop;

import ru.itmo.degtiarenko.aop.pack1.Methods;
import ru.itmo.degtiarenko.aop.profile.Profiler;

public class CheckStatic {
    public static void main(String[] args) throws InterruptedException {
        Profiler.setPackageName("ru.itmo.degtiarenko.aop.pack1");
        Double d = 4.0;

        Methods.factorial(d.intValue());
        Methods.sleepCall(1.0);
        Methods.sleepCall(2.0);

        Profiler.getInstance().printStats(System.out);
    }
}

package ru.itmo.degtiarenko.aop;

import ru.itmo.degtiarenko.aop.pack2.Methods;
import ru.itmo.degtiarenko.aop.profile.Profiler;

public class CheckNonStatic {
    public static void main(String[] args) throws InterruptedException {
        Profiler.setPackageName("ru.itmo.degtiarenko.aop.pack2");
        Double d = 4.0;

        new Methods().factorial(d.intValue());
        new Methods().sleepCall(1.0);
        new Methods().sleepCall(2.0);

        Profiler.getInstance().printStats();
    }
}

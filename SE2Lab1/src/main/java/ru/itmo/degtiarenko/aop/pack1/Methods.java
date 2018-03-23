package ru.itmo.degtiarenko.aop.pack1;

public class Methods {
    private Methods() { }

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;
    }

    public static void sleepCall(double seconds) throws InterruptedException {
        Thread.sleep(Double.valueOf(seconds * 1000).longValue());
    }
}

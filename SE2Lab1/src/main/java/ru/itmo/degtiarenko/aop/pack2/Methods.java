package ru.itmo.degtiarenko.aop.pack2;

public class Methods {
    public Methods() { }

    public  int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;
    }

    public void sleepCall(double seconds) throws InterruptedException {
        Thread.sleep(Double.valueOf(seconds * 1000).longValue());
    }
}

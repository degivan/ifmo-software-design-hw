package ru.itmo.degtiarenko.aop;

public class Factorial {
    public int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;
    }
}

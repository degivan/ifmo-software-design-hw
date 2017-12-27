package ru.itmo.degtiarenko.hw.tokens;

import ru.itmo.degtiarenko.hw.visitors.TokenVisitor;

import java.math.BigInteger;

public class NumberToken implements Token {
    private final BigInteger number;

    private NumberToken(BigInteger number) {
        this.number = number;
    }

    public NumberToken(String number) {
        this(BigInteger.valueOf(Long.valueOf(number)));
    }

    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}

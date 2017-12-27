package ru.itmo.degtiarenko.hw.visitors;

import ru.itmo.degtiarenko.hw.tokens.Brace;
import ru.itmo.degtiarenko.hw.tokens.NumberToken;
import ru.itmo.degtiarenko.hw.tokens.Operation;

import java.math.BigInteger;

public class CalcVisitor implements TokenVisitor {
    public void visit(NumberToken token) {

    }

    public void visit(Brace token) {

    }

    public void visit(Operation token) {

    }

    public BigInteger getResult() {
        return null;
    }
}

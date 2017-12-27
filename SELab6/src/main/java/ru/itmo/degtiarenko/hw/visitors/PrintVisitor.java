package ru.itmo.degtiarenko.hw.visitors;

import ru.itmo.degtiarenko.hw.tokens.Brace;
import ru.itmo.degtiarenko.hw.tokens.NumberToken;
import ru.itmo.degtiarenko.hw.tokens.Operation;

public class PrintVisitor implements TokenVisitor {
    public void visit(NumberToken token) {
        System.out.print(String.format("NUMBER(%s) ", token.getNumber().toString()));
    }

    public void visit(Brace token) {
        System.out.print("BRACE ");
    }

    public void visit(Operation token) {
        System.out.print(String.format("%s ", token.getType().getSymbol()));
    }
}

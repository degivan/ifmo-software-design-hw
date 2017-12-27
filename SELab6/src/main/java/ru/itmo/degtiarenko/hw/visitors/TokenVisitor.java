package ru.itmo.degtiarenko.hw.visitors;

import ru.itmo.degtiarenko.hw.tokens.Brace;
import ru.itmo.degtiarenko.hw.tokens.NumberToken;
import ru.itmo.degtiarenko.hw.tokens.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(Brace token);

    void visit(Operation token);
}

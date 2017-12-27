package ru.itmo.degtiarenko.hw.tokens;

import ru.itmo.degtiarenko.hw.visitors.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}

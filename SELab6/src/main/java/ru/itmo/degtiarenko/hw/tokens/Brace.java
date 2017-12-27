package ru.itmo.degtiarenko.hw.tokens;

import ru.itmo.degtiarenko.hw.visitors.TokenVisitor;

public class Brace implements Token {
    private final BraceType type;

    public Brace(BraceType type) {
        this.type = type;
    }

    public Brace(char symbol) {
        switch (symbol) {
            case '(':
                type = BraceType.OPENED;
                break;
            default:
                type = BraceType.CLOSED;
        }
    }

    public void accept(TokenVisitor visitor) {

    }

    public enum BraceType {
        OPENED('('), CLOSED(')');

        public char getSymbol() {
            return symbol;
        }

        private final char symbol;

        BraceType(char symbol) {
            this.symbol = symbol;
        }
    }
}

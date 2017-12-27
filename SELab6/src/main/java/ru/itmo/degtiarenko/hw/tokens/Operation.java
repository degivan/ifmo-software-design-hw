package ru.itmo.degtiarenko.hw.tokens;

import ru.itmo.degtiarenko.hw.visitors.TokenVisitor;

public class Operation implements Token {
    private final OperationType type;

    public Operation(OperationType type) {
        this.type = type;
    }

    public Operation(char symbol) {
        switch (symbol) {
            case '+':
                type = OperationType.PLUS;
                break;
            case '-':
                type = OperationType.MINUS;
                break;
            case '*':
                type = OperationType.MULTIPLY;
                break;
            default:
                type = OperationType.DIVIDE;
        }
    }

    public void accept(TokenVisitor visitor) {

    }

    public enum OperationType {
        PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('/');

        OperationType(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

        private final char symbol;
    }
}

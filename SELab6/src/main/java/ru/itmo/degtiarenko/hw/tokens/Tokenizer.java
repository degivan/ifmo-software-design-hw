package ru.itmo.degtiarenko.hw.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Tokenizer {
    private final Set<Character> OPERATIONS = stringToCharacterSet("+-*/");
    private final Set<Character> BRACES = stringToCharacterSet("()");

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        State state = new StartState(input, 0);
        while (!(state instanceof EndState)) {
            state = state.nextState(tokens);
        }
        return tokens;
    }

    private static Set<Character> stringToCharacterSet(String string) {
        return string.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
    }

    private abstract class State {
        String input;
        int pos;

        Character prev = null;

        State(String input, int pos) {
            this.input = input;
            this.pos = pos;
        }

        State(String input, int pos, Character prev) {
            this.pos = pos;
            this.input = input;
            this.prev = prev;
        }

        public abstract State nextState(List<Token> tokens);
    }

    private class StartState extends State {
        StartState(String input, int pos) {
            super(input, pos);
        }

        StartState(String input, int pos, char symbol) {
            super(input, pos, symbol);
        }

        @Override
        public State nextState(List<Token> tokens) {
            if (pos == input.length()) {
                return new EndState(input, pos);
            }
            char symbol = input.charAt(pos);
            if (Character.isDigit(symbol) || ((prev == null || prev == '(') && symbol == '-')) {
                return new NumberState(input, pos, symbol);
            } else if (OPERATIONS.contains(symbol)) {
                tokens.add(new Operation(symbol));
            } else if (BRACES.contains(symbol)) {
                tokens.add(new Brace(symbol));
            } else {
                throw new RuntimeException("Incorrect state: " + input + " " + pos);
            }
            pos++;
            return new StartState(input, pos, symbol);
        }
    }

    private class EndState extends State {
        EndState(String input, int pos) {
            super(input, pos);
        }

        @Override
        public State nextState(List<Token> tokens) {
            throw new UnsupportedOperationException();
        }
    }

    private class NumberState extends State {
        private StringBuilder currentNumber;

        NumberState(String input, int pos, StringBuilder currentNumber) {
            super(input, pos);
            this.currentNumber = currentNumber;
        }

        NumberState(String input, int pos, char symbol) {
            this(input, pos, symbol, new StringBuilder());
        }

        NumberState(String input, int pos, char symbol, StringBuilder currentNumber) {
            super(input, pos, symbol);
            this.currentNumber = currentNumber;
        }

        @Override
        public State nextState(List<Token> tokens) {
            final String currNumber = currentNumber.toString();
            if (pos == input.length()) {
                tokens.add(new NumberToken(currNumber));
                return new EndState(input, pos);
            }
            char symbol = input.charAt(pos);
            if (Character.isDigit(symbol) || (currentNumber.length() == 0 && symbol =='-')) {
                currentNumber.append(symbol);
                pos++;
                return new NumberState(input, pos, symbol, currentNumber);
            } else {
                tokens.add(new NumberToken(currNumber));
                return new StartState(input, pos, currNumber.charAt(currNumber.length() - 1));
            }
        }
    }
}

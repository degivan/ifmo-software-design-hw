package ru.itmo.degtiarenko.hw.visitors;

import ru.itmo.degtiarenko.hw.tokens.Brace;
import ru.itmo.degtiarenko.hw.tokens.NumberToken;
import ru.itmo.degtiarenko.hw.tokens.Operation;
import ru.itmo.degtiarenko.hw.tokens.Operation.OperationType;
import ru.itmo.degtiarenko.hw.tokens.Token;

import java.util.*;

import static ru.itmo.degtiarenko.hw.tokens.Brace.BraceType.*;

public class ParserVisitor implements TokenVisitor {
    private static final Map<OperationType, Integer> priorities = new HashMap<>();
    private Stack<Token> stack = new Stack<>();
    private List<Token> tokens = new ArrayList<>();

    static {
        priorities.put(OperationType.PLUS, 0);
        priorities.put(OperationType.MINUS, 1);
        priorities.put(OperationType.MULT, 2);
        priorities.put(OperationType.DIV, 3);
    }

    public void visit(NumberToken token) {
        tokens.add(token);
    }

    public void visit(Brace token) {
        try {
            if(token.getType() == OPENED) {
                stack.push(token);
            } else {
                Token elem = stack.pop();
                while(!(elem instanceof Brace)) {
                    tokens.add(elem);
                    elem = stack.pop();
                }
            }
        } catch(EmptyStackException e) {
            throw new RuntimeException("Incorrect form!", e);
        }
    }

    public void visit(Operation token) {
        OperationType type = token.getType();
        int prior = priorities.get(type);
        while(!(stack.isEmpty() || firstElemLessPrior(prior))) {
            tokens.add(stack.pop());
        }
        stack.push(token);
    }

    private boolean firstElemLessPrior(int prior) {
        final Token token = stack.peek();
        if(token instanceof Operation) {
            if (priorities.get(((Operation)token).getType()) > prior) {
                return false;
            }
        }
        return true;
    }

    public List<Token> getTokens() {
        while(!stack.isEmpty()) {
            tokens.add(stack.pop());
        }
        return tokens;
    }
}

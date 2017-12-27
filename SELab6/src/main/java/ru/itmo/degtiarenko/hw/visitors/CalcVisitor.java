package ru.itmo.degtiarenko.hw.visitors;

import ru.itmo.degtiarenko.hw.tokens.Brace;
import ru.itmo.degtiarenko.hw.tokens.NumberToken;
import ru.itmo.degtiarenko.hw.tokens.Operation;
import ru.itmo.degtiarenko.hw.tokens.Token;

import java.math.BigInteger;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private Stack<Token> stack = new Stack<>();

    public void visit(NumberToken token) {
        stack.push(token);
    }

    public void visit(Brace token) {
        throw new IllegalArgumentException("Braces are not allowed in polish notation!");
    }

    public void visit(Operation token) {
        BigInteger op2 = ((NumberToken) stack.pop()).getNumber();
        BigInteger op1 = ((NumberToken) stack.pop()).getNumber();
        BigInteger result;
        switch(token.getType()) {
            case PLUS:
                result = op1.add(op2);
                break;
            case MINUS:
                result = op1.subtract(op2);
                break;
            case MULT:
                result = op1.multiply(op2);
                break;
            default:
                result = op1.divide(op2);
        }
        stack.push(new NumberToken(result));
    }

    public BigInteger getResult() {
        return ((NumberToken) stack.pop()).getNumber();
    }
}

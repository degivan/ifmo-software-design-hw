package ru.itmo.degtiarenko.hw.visitors;

import org.junit.Test;
import ru.itmo.degtiarenko.hw.tokens.NumberToken;
import ru.itmo.degtiarenko.hw.tokens.Operation;
import ru.itmo.degtiarenko.hw.tokens.Token;
import ru.itmo.degtiarenko.hw.tokens.Tokenizer;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ParserVisitorTest {

    @Test
    public void testSum() {
        List<Token> tokens = visitExpression("123+456");
        assertTrue(tokens.size() == 3);
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertTrue(tokens.get(1) instanceof NumberToken);
        assertTrue(tokens.get(2) instanceof Operation);
    }

    @Test
    public void testBraces() {
        List<Token> tokens = visitExpression("(123+456)*3");
        assertTrue(tokens.size() == 5);
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertTrue(tokens.get(1) instanceof NumberToken);
        assertTrue(tokens.get(2) instanceof Operation);
        assertTrue(tokens.get(3) instanceof NumberToken);
        assertTrue(tokens.get(4) instanceof Operation);
    }

    @Test
    public void testMinus() {
        List<Token> tokens = visitExpression("-1-2");
        assertTrue(tokens.size() == 3);
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertTrue(tokens.get(1) instanceof NumberToken);
        assertTrue(tokens.get(2) instanceof Operation);
    }


    private List<Token> visitExpression(String input) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);
        ParserVisitor visitor = new ParserVisitor();
        for (Token token: tokens) {
            token.accept(visitor);
        }
        return visitor.getTokens();
    }
}

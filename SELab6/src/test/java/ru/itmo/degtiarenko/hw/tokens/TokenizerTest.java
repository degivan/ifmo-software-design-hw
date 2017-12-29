package ru.itmo.degtiarenko.hw.tokens;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TokenizerTest {

    @Test
    public void testNumber() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("123");
        assertTrue(tokens.size() == 1);
        assertTrue(tokens.get(0) instanceof NumberToken);
        System.out.println(tokens.get(0));
    }

    @Test
    public void testSum() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("12+15");
        assertTrue(tokens.size() == 3);
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertTrue(tokens.get(1) instanceof Operation);
        assertTrue(tokens.get(2) instanceof NumberToken);
    }

    @Test
    public void testNegativeStart() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("-12+15");
        assertTrue(tokens.size() == 3);
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertTrue(tokens.get(1) instanceof Operation);
        assertTrue(tokens.get(2) instanceof NumberToken);
    }

    @Test
    public void testNegativeBrace() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("3*(-12+2)");
        assertTrue(tokens.size() == 7);
        assertTrue(tokens.get(0) instanceof NumberToken);
        assertTrue(tokens.get(1) instanceof Operation);
        assertTrue(tokens.get(2) instanceof Brace);
        assertTrue(tokens.get(3) instanceof NumberToken);
        assertTrue(tokens.get(4) instanceof Operation);
        assertTrue(tokens.get(5) instanceof NumberToken);
        assertTrue(tokens.get(6) instanceof Brace);
    }

    @Test
    public void testBraces() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize("(12+15)*3");
        assertTrue(tokens.size() == 7);
        assertTrue(tokens.get(0) instanceof Brace);
        assertTrue(tokens.get(1) instanceof NumberToken);
        assertTrue(tokens.get(2) instanceof Operation);
        assertTrue(tokens.get(3) instanceof NumberToken);
        assertTrue(tokens.get(4) instanceof Brace);
        assertTrue(tokens.get(5) instanceof Operation);
        assertTrue(tokens.get(6) instanceof NumberToken);
    }
}

package ru.itmo.degtiarenko.hw.visitors;

import org.junit.Test;
import ru.itmo.degtiarenko.hw.tokens.Token;
import ru.itmo.degtiarenko.hw.tokens.Tokenizer;

import java.util.List;

public class PrintVisitorTest {
    @Test
    public void testExpression() {
        visitExpression("123+456");
        visitExpression("(123+456)*13");
        visitExpression("1-2+3*15");
        visitExpression("1+(2-3)*15");
        visitExpression("-1+2");
        visitExpression("3*(-1+2)");
    }

    private void visitExpression(String input) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);
        ParserVisitor visitor = new ParserVisitor();
        PrintVisitor printVisitor = new PrintVisitor();
        for (Token token: tokens) {
            token.accept(visitor);
        }
        tokens = visitor.getTokens();
        for(Token token: tokens) {
            token.accept(printVisitor);
        }
        System.out.println();
    }
}

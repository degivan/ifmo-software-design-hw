package ru.itmo.degtiarenko.hw.visitors;

import org.junit.Test;
import ru.itmo.degtiarenko.hw.tokens.Token;
import ru.itmo.degtiarenko.hw.tokens.Tokenizer;

import java.util.List;

public class CalcVisitorTest {
    @Test
    public void testExpression() {
        visitExpression("123+456");
        visitExpression("(123+456)*13");
        visitExpression("1-2+3*15");
        visitExpression("1+(2-3)*15");
    }


    private void visitExpression(String input) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(input);
        ParserVisitor visitor = new ParserVisitor();
        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token: tokens) {
            token.accept(visitor);
        }
        tokens = visitor.getTokens();
        for(Token token: tokens) {
            token.accept(calcVisitor);
        }
        System.out.println(calcVisitor.getResult());
    }
}

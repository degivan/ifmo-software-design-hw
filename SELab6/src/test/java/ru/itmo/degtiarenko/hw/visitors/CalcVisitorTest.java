package ru.itmo.degtiarenko.hw.visitors;

import org.junit.Test;
import ru.itmo.degtiarenko.hw.tokens.Token;
import ru.itmo.degtiarenko.hw.tokens.Tokenizer;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalcVisitorTest {
    @Test
    public void testExpression() {
        assertEquals(visitExpression("123+456").toString(),"579");
        assertEquals(visitExpression("(123+456)*13").toString(), "7527");
        assertEquals(visitExpression("1-2+3*15").toString(), "44");
        assertEquals(visitExpression("1+(2-3)*15").toString(), "-14");
        assertEquals(visitExpression("-1+2").toString(), "1");
        assertEquals(visitExpression("-1*(-2+3)").toString(), "-1");
        assertEquals(visitExpression("3*(-1+2)").toString(), "3");
    }


    private BigInteger visitExpression(String input) {
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
        final BigInteger result = calcVisitor.getResult();
        System.out.println(result);
        return result;
    }
}

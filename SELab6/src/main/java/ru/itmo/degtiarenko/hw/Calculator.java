package ru.itmo.degtiarenko.hw;

import ru.itmo.degtiarenko.hw.tokens.Token;
import ru.itmo.degtiarenko.hw.tokens.Tokenizer;
import ru.itmo.degtiarenko.hw.visitors.CalcVisitor;
import ru.itmo.degtiarenko.hw.visitors.ParserVisitor;
import ru.itmo.degtiarenko.hw.visitors.PrintVisitor;
import ru.itmo.degtiarenko.hw.visitors.TokenVisitor;

import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();
            if (input.equals("finish")) {
                break;
            }
            Tokenizer tokenizer = new Tokenizer();
            ParserVisitor parserVisitor = new ParserVisitor();
            CalcVisitor calcVisitor = new CalcVisitor();
            PrintVisitor printVisitor = new PrintVisitor();
            List<Token> tokens = tokenizer.tokenize(input);
            visitTokens(parserVisitor, tokens);
            tokens = parserVisitor.getTokens();
            visitTokens(calcVisitor, tokens);
            visitTokens(printVisitor, tokens);
            System.out.println();
            System.out.println(calcVisitor.getResult());
        }
    }

    private static void visitTokens(TokenVisitor visitor, List<Token> tokens) {
        for (Token token: tokens) {
          token.accept(visitor);
        }
    }
}

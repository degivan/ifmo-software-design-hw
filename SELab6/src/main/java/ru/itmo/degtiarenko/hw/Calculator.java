package ru.itmo.degtiarenko.hw;

import ru.itmo.degtiarenko.hw.tokens.Token;
import ru.itmo.degtiarenko.hw.tokens.Tokenizer;

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
            List<Token> tokens = tokenizer.tokenize(input);

        }
    }
}

package ssd.labs.calculator.math;

import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Parses tokens from string
 */
@NoArgsConstructor
class Lexer {

    private static Map<Character, BiFunction<Character, Integer, Token>> simpleTokenSupplierMap = new HashMap<>();

    static {
        Operator.getCharOperatorMap().keySet().forEach(
                (k) -> simpleTokenSupplierMap.put(k, OperatorToken::new)
        );
    }

    public List<Token> parse(String inputString) throws ParseException {
        var tokens = new LinkedList<Token>();
        var buffer = new StringBuilder();
        var chars = inputString.chars().mapToObj(val -> (char) val);
        var position = new AtomicInteger(-1);

        Consumer<Character> processFunc = (Character val) -> {
            if (!simpleTokenSupplierMap.containsKey(val)) { // part of operand
                buffer.append(val);
                return;
            }
            if (buffer.length() > 0) {  // create operand from existing data
                var operand = buffer.toString();
                buffer.delete(0, buffer.length());
                tokens.add(new OperandToken(operand, position.get() - 1));
            }
            // add operator to tokens
            tokens.add(simpleTokenSupplierMap.get(val).apply(val, position.get()));
        };

        Consumer<Character> processWrapFunc = (Character val) -> {
            position.addAndGet(1);
            if (val == ' ')
                return;
            processFunc.accept(val);
        };

        try {
            chars.forEach(processWrapFunc);
            if (buffer.length() > 0) {
                tokens.add(new OperandToken(buffer.toString(), position.get()));
            }
        } catch (Throwable throwable) {
            throw new ParseException("Exception while parsing input for calculator: " + throwable.toString(), position.get());
        }
        return tokens;
    }
}

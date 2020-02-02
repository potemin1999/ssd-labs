package ssd.labs.calculator.math;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Operator {

    OPERATOR_ADDITION(0x0201),
    OPERATOR_SUBTRACTION(0x0202),
    OPERATOR_MULTIPLICATION(0x0303),
    OPERATOR_DIVISION(0x0304),
    OPERATOR_PAREN_OPEN(0x0005),
    OPERATOR_PAREN_CLOSE(0x0006),
    OPERATOR_POWER(0x1407);

    private static Map<Character, Operator> charOperatorMap = Map.of(
            '+', OPERATOR_ADDITION,
            '-', OPERATOR_SUBTRACTION,
            '*', OPERATOR_MULTIPLICATION,
            '/', OPERATOR_DIVISION,
            '(', OPERATOR_PAREN_OPEN,
            ')', OPERATOR_PAREN_CLOSE,
            '^', OPERATOR_POWER
    );

    int opCode;

    Operator(int opCode) {
        this.opCode = opCode;
    }

    public int getPrecedence() {
        return (0x0f00 & opCode) >> 8;
    }

    public boolean isRtlAssociative() {
        return (0x1000 & opCode) == 0x1000;
    }

    public static Operator ofChar(char operator) {
        return charOperatorMap.getOrDefault(operator, null);
    }

    public static Map<Character, Operator> getCharOperatorMap() {
        return charOperatorMap;
    }
}

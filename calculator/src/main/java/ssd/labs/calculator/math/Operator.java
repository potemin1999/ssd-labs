package ssd.labs.calculator.math;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * Operator description
 */
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Operator implements BiFunction<Double, Double, Double> {

    @SuppressWarnings("Convert2MethodRef")
    OPERATOR_ADDITION(0x0201,
            (a, b) -> a + b),
    OPERATOR_SUBTRACTION(0x0202,
            (a, b) -> a - b),
    OPERATOR_MULTIPLICATION(0x0303,
            (a, b) -> a * b),
    OPERATOR_DIVISION(0x0304,
            (a, b) -> a / b),
    OPERATOR_PAREN_OPEN(0x0005, null),
    OPERATOR_PAREN_CLOSE(0x0006, null),
    OPERATOR_POWER(0x1407,
            Math::pow);

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
    BiFunction<Double, Double, Double> execFunction;

    Operator(int opCode, BiFunction<Double, Double, Double> exec) {
        this.opCode = opCode;
        this.execFunction = exec;
    }

    public static Operator ofChar(char operator) {
        return charOperatorMap.getOrDefault(operator, null);
    }

    public static Map<Character, Operator> getCharOperatorMap() {
        return charOperatorMap;
    }

    public int getPrecedence() {
        return (0x0f00 & opCode) >> 8;
    }

    public boolean isRtlAssociative() {
        return (0x1000 & opCode) == 0x1000;
    }

    @Override
    public Double apply(Double aDouble, Double aDouble2) {
        return execFunction.apply(aDouble, aDouble2);
    }
}

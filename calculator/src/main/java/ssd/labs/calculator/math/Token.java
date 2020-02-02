package ssd.labs.calculator.math;

public interface Token {

    enum Type {
        OPERAND,
        OPERATOR,
        FUNCTION,
    }

    Type getType();

}

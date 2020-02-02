package ssd.labs.calculator.math;

/**
 * Generic token
 */
public interface Token {

    Type getType();

    enum Type {
        OPERAND,
        OPERATOR,
        FUNCTION,
    }

}

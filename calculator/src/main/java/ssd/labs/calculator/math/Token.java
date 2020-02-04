package ssd.labs.calculator.math;

/**
 * Generic token
 */
public interface Token {

    Type getType();

    default int getPosition() {
        return 0;
    }

    enum Type {
        OPERAND,
        OPERATOR,
        FUNCTION,
    }

}

package ssd.labs.calculator.math;

import lombok.extern.java.Log;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Do all calculations
 */
@Log
class Calculator {

    public double calculate(Collection<Token> rpnTokens) {
        try {
            return doCalculate(rpnTokens);
        } catch (EmptyStackException e) {
            System.err.println("Empty stack exception occurred due to incorrect expression format\n" +
                    " probably you have used unary minus, which is not currently supported");
        }
        return 0.0;
    }

    public double doCalculate(Collection<Token> rpnTokens) {
        Stack<Double> stack = new Stack<>();
        for (var token : rpnTokens) {
            if (token.getType() == Token.Type.OPERATOR) {
                var operator = ((OperatorToken) token);
                Double b = stack.pop();
                Double a = stack.pop();

                if (b == 0 && operator.getOperator() == Operator.OPERATOR_DIVISION) {
                    System.err.println("Arithmetic error at position " +
                            token.getPosition() + ": division by zero");
                }
                var c = operator.getOperator().apply(a, b);
                stack.push(c);
                continue;
            }
            if (token.getType() == Token.Type.OPERAND) {
                stack.push(((OperandToken) token).getValue());
                continue;
            }
            LOG.warning("Token of unknown type " + token.toString());
        }
        return stack.pop();
    }

}

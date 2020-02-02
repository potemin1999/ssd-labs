package ssd.labs.calculator.math;

import lombok.extern.java.Log;

import java.util.Collection;
import java.util.Stack;

/**
 * Do all calculations
 */
@Log
class Calculator {

    public double calculate(Collection<Token> rpnTokens) {
        Stack<Double> stack = new Stack<>();
        for (var token : rpnTokens) {
            if (token.getType() == Token.Type.OPERATOR) {
                var b = stack.pop();
                var a = stack.pop();
                var c = ((OperatorToken) token).getOperator().apply(a, b);
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

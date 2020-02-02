package ssd.labs.calculator.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

public class RpnTransformer implements Function<Collection<Token>, Collection<Token>> {

    @Override
    public Collection<Token> apply(Collection<Token> tokens) {
        var stack = new ArrayList<Token>(tokens.size() >> 1);
        var output = new ArrayList<Token>(tokens.size());

        Supplier<Boolean> condition1 = () -> {
            var lastToken = stack.get(stack.size() - 1);
            var operatorLastToken = (OperatorToken) lastToken;
            return operatorLastToken.getOperator() != Operator.OPERATOR_PAREN_OPEN;
        };

        Function<Token, Boolean> condition3 = (token) -> {
            var top = stack.isEmpty() ? null : stack.get(stack.size() - 1);
            if (top == null)
                return false;
            // there is a function at the top of the operator stack
            var cond1 = (top.getType() == Token.Type.FUNCTION);
            // there is an operator at the top of the operator stack with greater precedence
            var cond2 = (top.getType() == Token.Type.OPERATOR
                    && ((OperatorToken) top).getOperator().getPrecedence() > ((OperatorToken) token).getOperator().getPrecedence());
            // the operator at the top of the operator stack has equal precedence and the token is left associative
            var cond3 = (top.getType() == Token.Type.OPERATOR
                    && ((OperatorToken) top).getOperator().getPrecedence() == ((OperatorToken) token).getOperator().getPrecedence()
                    && !((OperatorToken) token).getOperator().isRtlAssociative());
            // the operator at the top of the operator stack is not a left parenthesis
            var cond4 = (top.getType() == Token.Type.OPERATOR
                    && ((OperatorToken) top).getOperator() != Operator.OPERATOR_PAREN_OPEN);

            return (cond1 || cond2 || cond3) && cond4;
        };

        for (var token : tokens) {
            if (token.getType() == Token.Type.OPERAND) {
                output.add(token);
                continue;
            }
//            if (token.getType() == Token.Type.FUNCTION) {
//                stack.add(token);
//                continue;
//            }
            if (token.getType() == Token.Type.OPERATOR) {
                var operatorToken = (OperatorToken) token;
                if (operatorToken.getOperator() == Operator.OPERATOR_PAREN_OPEN) {
                    stack.add(operatorToken);
                    continue;
                }
                if (operatorToken.getOperator() == Operator.OPERATOR_PAREN_CLOSE) {
                    while (condition1.get()) {
                        output.add(stack.remove(stack.size() - 1));
                    }
                    stack.remove(stack.size() - 1);
                    continue;
                }
                while (condition3.apply(token)) {
                    output.add(stack.remove(stack.size() - 1));
                }
                stack.add(token);
            }
            //var top = stack.isEmpty() ? null : stack.get(stack.size() - 1);
//            while (condition2.apply(token)) {
//                output.add(stack.remove(stack.size() - 1));
//            }
//            stack.add(token);
        }

        Collections.reverse(stack);
        output.addAll(stack);
        return output;
    }


}

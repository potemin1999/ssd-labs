package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RpnTransformerTest {

    @Test
    public void test1() {
        var transformer = new RpnTransformer();
        var inputTokens = List.of(
                new OperatorToken(Operator.OPERATOR_PAREN_OPEN),
                new OperandToken("2"),
                new OperatorToken(Operator.OPERATOR_ADDITION),
                new OperandToken("5.0"),
                new OperatorToken(Operator.OPERATOR_SUBTRACTION),
                new OperandToken("3"),
                new OperatorToken(Operator.OPERATOR_PAREN_CLOSE),
                new OperatorToken(Operator.OPERATOR_MULTIPLICATION),
                new OperandToken("4")
        );
        var expectedTokens = List.of(
                new OperandToken("2"),
                new OperandToken("5.0"),
                new OperatorToken(Operator.OPERATOR_ADDITION),
                new OperandToken("3"),
                new OperatorToken(Operator.OPERATOR_SUBTRACTION),
                new OperandToken("4"),
                new OperatorToken(Operator.OPERATOR_MULTIPLICATION)
        );
        var actualTokens = transformer.apply(inputTokens);
        Assert.assertEquals(expectedTokens, actualTokens);
    }
}

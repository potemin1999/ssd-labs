package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RpnTransformerTest {

    @Test
    public void test1() {
        var transformer = new RpnTransformer();
        var inputTokens = List.of(
                new OperatorToken(Operator.OPERATOR_PAREN_OPEN, 0),
                new OperandToken("2", 0),
                new OperatorToken(Operator.OPERATOR_ADDITION, 0),
                new OperandToken("5.0", 0),
                new OperatorToken(Operator.OPERATOR_SUBTRACTION, 0),
                new OperandToken("3", 0),
                new OperatorToken(Operator.OPERATOR_PAREN_CLOSE, 0),
                new OperatorToken(Operator.OPERATOR_MULTIPLICATION, 0),
                new OperandToken("4", 0)
        );
        var expectedTokens = List.of(
                new OperandToken("2", 0),
                new OperandToken("5.0", 0),
                new OperatorToken(Operator.OPERATOR_ADDITION, 0),
                new OperandToken("3", 0),
                new OperatorToken(Operator.OPERATOR_SUBTRACTION, 0),
                new OperandToken("4", 0),
                new OperatorToken(Operator.OPERATOR_MULTIPLICATION, 0)
        );
        var actualTokens = transformer.apply(inputTokens);
        Assert.assertEquals(expectedTokens, actualTokens);
    }
}

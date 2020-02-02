package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

public class OperatorTests {

    @Test
    public void operatorOfChar() {
        Assert.assertEquals(Operator.ofChar('+'), Operator.OPERATOR_ADDITION);
        Assert.assertEquals(Operator.ofChar('-'), Operator.OPERATOR_SUBTRACTION);
        Assert.assertEquals(Operator.ofChar('*'), Operator.OPERATOR_MULTIPLICATION);
        Assert.assertEquals(Operator.ofChar('/'), Operator.OPERATOR_DIVISION);
        Assert.assertEquals(Operator.ofChar('('), Operator.OPERATOR_PAREN_OPEN);
        Assert.assertEquals(Operator.ofChar(')'), Operator.OPERATOR_PAREN_CLOSE);
        Assert.assertEquals(Operator.ofChar('^'), Operator.OPERATOR_POWER);
    }

    @Test
    public void assertAssociativity() {
        Assert.assertFalse(Operator.OPERATOR_ADDITION.isRtlAssociative());
        Assert.assertTrue(Operator.OPERATOR_POWER.isRtlAssociative());
    }
}

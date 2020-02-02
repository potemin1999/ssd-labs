package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class OperatorTokenTest {

    @Test
    public void assertType() {
        Assert.assertEquals(new OperatorToken(Operator.OPERATOR_DIVISION).getType(), Token.Type.OPERATOR);
    }

    @Test
    public void newTokenCorrect() {
        new OperatorToken('+');
    }

    @Test(expected = ParseException.class)
    public void newTokenIncorrect() {
        new OperatorToken('&');
    }
}

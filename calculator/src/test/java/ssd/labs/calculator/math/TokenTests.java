package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

public class TokenTests {

    @Test
    public void testType() {
        Token t = () -> Token.Type.OPERAND;
        Assert.assertEquals(0, t.getPosition());
    }
}

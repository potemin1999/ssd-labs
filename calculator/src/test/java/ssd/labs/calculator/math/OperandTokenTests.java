package ssd.labs.calculator.math;


import org.junit.Assert;
import org.junit.Test;

public class OperandTokenTests {

    @Test
    public void parsingTest() {
        Assert.assertEquals(4,
                new OperandToken("4", 0).getValue(), 0.0001);
        Assert.assertEquals(-4,
                new OperandToken("-4", 0).getValue(), 0.00001);
        Assert.assertEquals(0,
                new OperandToken("-0", 0).getValue(), 0.000001);
    }

    @Test
    public void newTokenCorrect() {
        Assert.assertEquals(8,
                new OperandToken(8, 0).getValue(), 0.000001);
    }
}

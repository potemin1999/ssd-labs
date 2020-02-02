package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.EmptyStackException;
import java.util.List;

public class CalculatorTests {

    @Test(expected = EmptyStackException.class)
    public void wrongTokensTest() {
        var calculator = new Calculator();
        var tokens = List.of((Token) () -> Token.Type.FUNCTION);
        calculator.calculate(tokens);
    }

    @Test
    public void testAllOperations() throws ParseException {
        var calculator = new Calculator();
        var tokensStr = "4*7 - 9/3 + 2^10"; // 28-3+1024=1049
        var tokensParsed = new Lexer().parse(tokensStr);
        var tokensRpn = new RpnTransformer().apply(tokensParsed);
        var result = calculator.calculate(tokensRpn);
        Assert.assertEquals(1049.0, result, 0.00001);
    }
}

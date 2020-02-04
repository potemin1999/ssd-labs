package ssd.labs.calculator.math;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.List;

public class LexerTests {

    @Test
    public void lexerTest1() throws ParseException {
        var lexer = new Lexer();
        var tokens = lexer.parse("(2 + 5.0)*4/2.0");
        var expectedTokens = List.of(
                new OperatorToken(Operator.OPERATOR_PAREN_OPEN, 0),
                new OperandToken("2", 2),
                new OperatorToken(Operator.OPERATOR_ADDITION, 3),
                new OperandToken("5.0", 7),
                new OperatorToken(Operator.OPERATOR_PAREN_CLOSE, 8),
                new OperatorToken(Operator.OPERATOR_MULTIPLICATION, 9),
                new OperandToken("4", 10),
                new OperatorToken(Operator.OPERATOR_DIVISION, 11),
                new OperandToken("2.0", 14)
        );
        Assert.assertEquals(expectedTokens, tokens);
    }

    @Test(expected = ParseException.class)
    public void lexerIncorrect() throws ParseException {
        var lexer = new Lexer();
        lexer.parse("(5+&&&(9)");
    }

}

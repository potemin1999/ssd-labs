package ssd.labs.calculator.cmd;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class CalculatorCmdTests {

    @Test
    public void testCompute() throws ParseException {
        CalculatorCmd calculatorCmd = new CalculatorCmd();
        double value = calculatorCmd.compute("(1+2+3+4)/5.0");
        Assert.assertEquals(2.0, value, 0.00001);
    }

    @Test
    public void testCalculate() {
        var environment = new CommandProcessor.DefaultEnvironment();
        var calculatorCmd = new CalculatorCmd();
        calculatorCmd.doCalculate(environment, new String[]{"2+3"});
        var history = environment.getHistoryStorage().getHistoryEntries();
        Assert.assertEquals(1, history.size());
        var entry = history.get(0);
        Assert.assertEquals("2+3", entry.getInput());
        Assert.assertEquals("5.0", entry.getResult());
    }

    @Test
    public void failToCalculate() {
        var environment = new CommandProcessor.DefaultEnvironment();
        var calculatorCmd = new CalculatorCmd();
        var ret = calculatorCmd.doCalculate(environment, new String[]{"coffee+tea"});
        Assert.assertEquals(1, ret);
    }
}

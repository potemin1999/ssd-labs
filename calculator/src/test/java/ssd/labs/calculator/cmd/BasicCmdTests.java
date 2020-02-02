package ssd.labs.calculator.cmd;

import org.junit.Assert;
import org.junit.Test;

public class BasicCmdTests {

    @Test
    public void testExit() {
        var environment = new CommandProcessor.CmdEnvironment();
        Assert.assertEquals(BasicCmd.doExit(environment, new String[0]), 255);
        Assert.assertEquals(BasicCmd.doExit(environment, new String[]{"anything"}), 255);
    }
}

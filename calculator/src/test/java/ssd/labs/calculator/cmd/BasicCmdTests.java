package ssd.labs.calculator.cmd;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class BasicCmdTests {

    @Test
    public void testExit() {
        var basicCmd = new BasicCmd();
        var atomicCounter = new AtomicInteger(0);
        var environment = new CommandProcessor.DefaultEnvironment((c) -> {
            atomicCounter.incrementAndGet();
            return true;
        });
        Assert.assertEquals(0, basicCmd.doExit(environment, new String[0]));
        Assert.assertEquals(0, basicCmd.doExit(environment, new String[]{"anything"}));
        Assert.assertEquals(2, atomicCounter.get());
    }
}

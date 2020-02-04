package ssd.labs.calculator.cmd;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandProcessorTests {

    @Test
    public void test1() {
        var testCmd = new TestCmd();
        var defCmd = new DefaultTestCmd();
        var wrongCmd = new WrongTestCmd();
        var cmds = List.of(testCmd, defCmd, wrongCmd);
        var cmd = new CommandProcessor(cmds);

        // check if TestCmd will be called
        var doThisBytes = "/do_this\n".getBytes();
        cmd.run(new ByteArrayInputStream(doThisBytes));
        Assert.assertEquals(1, testCmd.counter);

        // check if context would be saved
        cmd.run(new ByteArrayInputStream(doThisBytes));
        Assert.assertEquals(2, testCmd.counter);

        // check fallback handler
        var doThatBytes = "4-3\n".getBytes();
        cmd.run(new ByteArrayInputStream(doThatBytes));

        // check how it will handle exception while command executing
        var doCrashBytes = "/do_crash\n".getBytes();
        cmd.run(new ByteArrayInputStream(doCrashBytes));

        // check how it will handle wrong method signature
        var doNotBindBytes = "/do_not_bind\n".getBytes();
        cmd.run(new ByteArrayInputStream(doNotBindBytes));
    }

    @Test
    public void testExceptions() {
        var cmds = List.of(new BasicCmd(), new DefaultTestCmd());
        var env = new CommandProcessor.DefaultEnvironment(
                new HistoryStorage(10),
                System.out,
                null
        );
        var cmd = new CommandProcessor(env, cmds);


        var notFoundBytes = "/not_found\n".getBytes();
        cmd.run(new ByteArrayInputStream(notFoundBytes));

        var exitBytes = "/exit (who hugging cares about arguments)\n".getBytes();
        cmd.run(new ByteArrayInputStream(exitBytes));
    }

    @Test
    public void testIgnoredCall() {
        var counter = new AtomicInteger(0);
        var env = new CommandProcessor.DefaultEnvironment(
                call -> {
                    counter.incrementAndGet();
                    return false;
                }
        );
        env.envCall(Environment.Call.EXIT);
        Assert.assertEquals(1, counter.get());
    }


    static class TestCmd {
        private int counter = 0;

        @Cmd(value = "do_this")
        public int doThis(Environment env, String[] args) {
            env.getHistoryStorage().pushEntry(counter + "", counter + "");
            counter += 1;
            return 0;
        }
    }

    static class DefaultTestCmd {

        @Cmd(value = "", isDefault = true)
        public int doThat(Environment env, String[] args) {
            return 1;
        }

    }

    static class WrongTestCmd {
        @Cmd(value = "do_crash")
        public int doCrash(Environment env, String[] args) {
            throw new RuntimeException("This is crash");
        }

        @Cmd(value = "do_not_bind")
        public int doNotBind() {
            throw new RuntimeException("I will be never thrown");
        }
    }
}

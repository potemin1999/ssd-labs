package ssd.labs.calculator.cmd;

import lombok.extern.java.Log;

/**
 * Basic commands for calculator
 */
@Log
public class BasicCmd {

    @Cmd(value = "exit")
    public int doExit(Environment env, String[] args) {
        LOG.fine("Exit called");
        env.getOutputStream().println("exiting");
        env.envCall(Environment.Call.EXIT);
        return 0;
    }
}

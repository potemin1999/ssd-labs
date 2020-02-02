package ssd.labs.calculator.cmd;

import lombok.extern.java.Log;

@Log
public class BasicCmd {

    @Cmd(value = "exit")
    public static int doExit(Environment env, String[] args) {
        LOG.fine("Exiting...");
        return 255;
    }
}

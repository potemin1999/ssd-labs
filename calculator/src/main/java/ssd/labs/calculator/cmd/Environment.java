package ssd.labs.calculator.cmd;

import java.io.PrintStream;

/**
 * Environment for command execution
 */
public interface Environment {

    PrintStream getOutputStream();

    HistoryStorage getHistoryStorage();

    void envCall(Call call);

    enum Call {
        EXIT,
    }

    interface CallHandler {
        boolean onCall(Call call);
    }

}

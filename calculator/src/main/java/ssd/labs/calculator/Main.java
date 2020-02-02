package ssd.labs.calculator;

import lombok.extern.java.Log;
import ssd.labs.calculator.cmd.BasicCmd;
import ssd.labs.calculator.cmd.CommandProcessor;
import ssd.labs.calculator.cmd.HistoryStorage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log
public class Main {

    public static final String VERSION = "0.1";

    public static void main(String[] args) {
        LOG.finest("Starting my little calculator " + VERSION);

        var targetLogLevel = Level.ALL;
        var rootLogger = Logger.getLogger("");
        rootLogger.setLevel(targetLogLevel);
        for (var handler : rootLogger.getHandlers()) {
            handler.setLevel(targetLogLevel);
        }

        try {
            List<Object> cmdList = List.of(
                    new BasicCmd(),
                    new HistoryStorage.Printer()
            );
            CommandProcessor commandProcessor = new CommandProcessor(cmdList);
            commandProcessor.run();
        } catch (Throwable t) {
            LOG.severe("Fatal exception:" + t.toString());
            t.printStackTrace();
        }
    }

}

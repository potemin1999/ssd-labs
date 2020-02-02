package ssd.labs.calculator;

import lombok.extern.java.Log;
import ssd.labs.calculator.cmd.BasicCmd;
import ssd.labs.calculator.cmd.CalculatorCmd;
import ssd.labs.calculator.cmd.CommandProcessor;
import ssd.labs.calculator.cmd.HistoryStorage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program driver
 */
@Log
public class Main {

    public static final String VERSION = "v0.1";

    public static void setupLogging(String level) {
        var targetLogLevel = Level.parse(level);
        var rootLogger = Logger.getLogger("");
        rootLogger.setLevel(targetLogLevel);
        for (var handler : rootLogger.getHandlers()) {
            handler.setLevel(targetLogLevel);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            setupLogging(args[0]);
        }

        try {
            LOG.finest("Starting my little calculator " + VERSION);
            System.out.println("Starting my little calculator " + VERSION);
            List<Object> cmdList = List.of(
                    new BasicCmd(),
                    new CalculatorCmd(),
                    new HistoryStorage.Printer()
            );
            CommandProcessor commandProcessor = new CommandProcessor(cmdList);
            commandProcessor.run(System.in);
        } catch (Throwable t) {
            LOG.severe("Fatal exception:" + t.toString());
            t.printStackTrace();
        }

    }

}

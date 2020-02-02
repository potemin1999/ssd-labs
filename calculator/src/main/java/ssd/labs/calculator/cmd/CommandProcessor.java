package ssd.labs.calculator.cmd;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import ssd.labs.calculator.math.Lexer;
import ssd.labs.calculator.math.RpnTransformer;
import ssd.labs.calculator.math.Token;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log
@FieldDefaults(level = AccessLevel.PACKAGE)
public class CommandProcessor {


    Lexer lexer = new Lexer();
    RpnTransformer rpnTransformer = new RpnTransformer();
    Environment environment = new CmdEnvironment();

    Map<String, Function<String[], Integer>> commandMap = new HashMap<>();

    public CommandProcessor(List<Object> cmdExecutionProviderList) {
        cmdExecutionProviderList.stream()
                .flatMap(provider -> Arrays.stream(provider.getClass().getMethods()))
                .filter(m -> m.isAnnotationPresent(Cmd.class))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .forEach(method -> {
                    var name = method.getAnnotation(Cmd.class).value();
                    commandMap.put(name, makeCommandFunction(method));
                    LOG.finer("Found command " + name);
                });
    }

    private Function<String[], Integer> makeCommandFunction(Method cmdMethod) {
        return (strings) -> {
            try {
                var retValue = cmdMethod.invoke(null, environment, strings);
                return (retValue instanceof Integer) ? (Integer) retValue : 0;
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOG.warning("Cmd execution failed: " + e.toString());
                e.printStackTrace();
                return 6;
            }
        };
    }

    public int run() {
        var scanner = new Scanner(System.in);
        for (; ; ) {
            var line = scanner.nextLine();
            if (line.startsWith("/")) {
                var cmdAndArgs = line.split(" ", 1);
                var cmdStr = cmdAndArgs[0].substring(1);
                var cmd = commandMap.getOrDefault(cmdStr, null);
                if (cmd == null) {
                    LOG.warning("Command not found: " + cmdStr);
                    break;
                }
                String[] arguments;
                if (cmdAndArgs.length > 1) {
                    arguments = cmdAndArgs[1].split(" ");
                } else {
                    arguments = new String[0];
                }
                var retVal = cmd.apply(arguments);
                if (retVal == 255) {
                    break;
                }
            } else {
                List<Token> tokens;
                try {
                    tokens = lexer.parse(line);
                } catch (ParseException e) {
                    LOG.warning(e.toString());
                    continue;
                }
                var tokensStr = tokens.stream().map(Object::toString).collect(Collectors.joining(" "));
                LOG.info("Tokens received: " + tokensStr);
                var rpnTokens = rpnTransformer.apply(tokens);
                var rpnTokensStr = rpnTokens.stream().map(Object::toString).collect(Collectors.joining(" "));
                LOG.info("RPN tokens: " + rpnTokensStr);
                environment.getHistoryStorage().pushEntry(line, " 0");
            }
        }
        return 0;
    }

    static class CmdEnvironment implements Environment {

        HistoryStorage historyStorage = new HistoryStorage(5);

        public HistoryStorage getHistoryStorage() {
            return historyStorage;
        }
    }
}

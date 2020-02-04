package ssd.labs.calculator.cmd;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ssd.labs.calculator.cmd.Environment.Call.EXIT;

/**
 * Reads commands and calls their processors
 */
@Log
@FieldDefaults(level = AccessLevel.PACKAGE)
public class CommandProcessor {

    Environment environment;
    Function<String[], Integer> defaultFunction = null;
    Map<String, Function<String[], Integer>> commandMap = new HashMap<>();

    public CommandProcessor(List<Object> cmdExecutionProviderList) {
        this(null, cmdExecutionProviderList);
    }

    public CommandProcessor(Environment environment, List<Object> cmdExecutionProviderList) {
        if (environment == null) {
            environment = new DefaultEnvironment(this::onCall);
        }
        this.environment = environment;
        cmdExecutionProviderList.forEach((object) -> Arrays.stream(object.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Cmd.class))
                .forEach(method -> {
                    var name = method.getAnnotation(Cmd.class).value();
                    var ref = Modifier.isStatic(method.getModifiers()) ? null : object;
                    var cmdFunction = makeCommandFunction(method, ref);
                    commandMap.put(name, cmdFunction);
                    if (method.getAnnotation(Cmd.class).isDefault()) {
                        defaultFunction = cmdFunction;
                    }
                    LOG.finer("Found command " + name);
                }));
    }

    private Function<String[], Integer> makeCommandFunction(Method cmdMethod, Object nullableRef) {
        return (strings) -> {
            try {
                var retValue = cmdMethod.invoke(nullableRef, environment, strings);
                return (retValue instanceof Integer) ? (Integer) retValue : 0;
            } catch (InvocationTargetException e) {
                if (e.getCause().getClass() == EnvCallException.class) {
                    throw (EnvCallException) e.getCause();
                }
                LOG.warning("Cmd execution failed: " + e.toString());
                e.printStackTrace();
                return 5;
            } catch (IllegalAccessException | IllegalArgumentException e) {
                LOG.warning("Cmd call failed: " + e.toString());
                e.printStackTrace();
                return 6;
            }
        };
    }

    public void execute(String line) {
        if (line.startsWith("/")) {
            var cmdAndArgs = line.split(" ", 2);
            var cmdStr = cmdAndArgs[0].substring(1);
            var cmd = commandMap.getOrDefault(cmdStr, null);
            if (cmd == null) {
                LOG.fine("Command not found: " + cmdStr);
                environment.getOutputStream().println("command not found: " + cmdStr);
                environment.getOutputStream().println("available commands: \n  " +
                        commandMap.keySet().stream()
                                .filter(s -> s.length() > 0)
                                .collect(Collectors.joining("\n  ")));
                return;
            }
            String[] arguments;
            if (cmdAndArgs.length > 1) {
                arguments = cmdAndArgs[1].split(" ");
            } else {
                arguments = new String[0];
            }
            var retVal = cmd.apply(arguments);
        } else {
            if (defaultFunction != null) {
                defaultFunction.apply(new String[]{line});
            }
        }
    }

    public void run(InputStream inputStream) {
        var scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            try {
                execute(line);
            } catch (EnvCallException e) {
                if (e.getCall() == EXIT) {
                    break;
                }
            }
        }
    }

    public boolean onCall(Environment.Call call) {
        throw new EnvCallException(call);
    }

    @Getter
    @AllArgsConstructor
    static class EnvCallException extends RuntimeException {
        Environment.Call call;
    }

    @AllArgsConstructor
    @Getter
    @FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
    static final class DefaultEnvironment implements Environment {

        HistoryStorage historyStorage;
        PrintStream outputStream;

        @Getter(value = AccessLevel.NONE)
        CallHandler callHandler;

        DefaultEnvironment() {
            this((call) -> false);
        }

        DefaultEnvironment(CallHandler callHandler) {
            this.historyStorage = new HistoryStorage(5);
            this.outputStream = System.out;
            this.callHandler = callHandler;
        }

        @Override
        public void envCall(Call call) {
            if (callHandler != null) {
                if (!callHandler.onCall(call))
                    LOG.warning("Environment call have not been catched");
            } else {
                LOG.warning("Ignored environment call: " + call.toString());
            }
        }
    }
}

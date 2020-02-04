package ssd.labs.calculator.cmd;

import lombok.extern.java.Log;
import ssd.labs.calculator.math.MathAPI;
import ssd.labs.calculator.math.Token;

import java.text.ParseException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Calculator cmd
 */
@Log
public class CalculatorCmd {

    MathAPI mathAPI = MathAPI.getDefault();

    public double compute(String line) throws ParseException {
        Collection<Token> tokens = mathAPI.tokenize(line);
        var tokensStr = tokens.stream().map(Object::toString).collect(Collectors.joining(" "));
        LOG.finer("Tokens received: " + tokensStr);

        var rpnTokens = mathAPI.toRpn(tokens);
        var rpnTokensStr = rpnTokens.stream().map(Object::toString).collect(Collectors.joining(" "));
        LOG.finer("RPN tokens: " + rpnTokensStr);

        return mathAPI.calculate(rpnTokens);
    }

    @Cmd(value = "", isDefault = true)
    public int doCalculate(Environment env, String[] args) {
        String line = args[0];
        double result;
        try {
            result = compute(line);
            env.getHistoryStorage().pushEntry(line, "" + result);
            env.getOutputStream().println(" = " + result);
        } catch (ParseException e) {
            System.err.println("at position " + e.getErrorOffset() + ": " + e.getMessage());
            env.getHistoryStorage().pushEntry(line, "error");
            return 1;
        }
        return 0;
    }
}

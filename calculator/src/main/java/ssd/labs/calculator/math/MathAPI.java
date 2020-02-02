package ssd.labs.calculator.math;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;
import java.util.Collection;

/**
 * Facade for current package functionality
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class MathAPI {

    private static MathAPI instance = null;

    public static MathAPI getDefault() {
        if (instance == null)
            instance = new DefaultMathAPI();
        return instance;
    }

    public abstract Collection<Token> tokenize(String line) throws ParseException;

    public abstract Collection<Token> toRpn(Collection<Token> infixTokens);

    public abstract double calculate(Collection<Token> rpnTokens);

    /**
     * Default math api, open to be extended by current package
     */
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class DefaultMathAPI extends MathAPI {

        Lexer lexer = new Lexer();
        RpnTransformer rpnTransformer = new RpnTransformer();
        Calculator calculator = new Calculator();

        @Override
        public Collection<Token> tokenize(String line) throws ParseException {
            return lexer.parse(line);
        }

        @Override
        public Collection<Token> toRpn(Collection<Token> infixTokens) {
            return rpnTransformer.apply(infixTokens);
        }

        @Override
        public double calculate(Collection<Token> rpnTokens) {
            return calculator.calculate(rpnTokens);
        }
    }
}

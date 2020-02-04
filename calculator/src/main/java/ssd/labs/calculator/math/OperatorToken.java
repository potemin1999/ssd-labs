package ssd.labs.calculator.math;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;
import java.util.Optional;

/**
 * Represents token, which can be executed over operands
 */
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class OperatorToken implements Token {

    Operator operator;
    int position;

    @SneakyThrows
    OperatorToken(char operatorChar, int position) throws RuntimeException {
        this.operator = Optional.ofNullable(Operator.ofChar(operatorChar)).orElseThrow(
                () -> new ParseException("unable to parse operator " + operatorChar, 0));
        this.position = position;
    }

    @Override
    public Type getType() {
        return Type.OPERATOR;
    }

}

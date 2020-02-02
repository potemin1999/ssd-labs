package ssd.labs.calculator.math;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class OperatorToken implements Token {

    Operator operator;

    @SneakyThrows
    public OperatorToken(char operatorChar) throws RuntimeException {
        operator = Optional.ofNullable(Operator.ofChar(operatorChar)).orElseThrow(
                () -> new ParseException("unable to parse operator " + operatorChar, 0));
    }

    @Override
    public Type getType() {
        return Type.OPERATOR;
    }

}

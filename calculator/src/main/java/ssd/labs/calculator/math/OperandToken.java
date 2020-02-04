package ssd.labs.calculator.math;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Represents token with value
 */
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OperandToken implements Token {

    double value;
    int position;

    OperandToken(String valueStr, int position) {
        this.value = Double.parseDouble(valueStr);
        this.position = position;
    }

    @Override
    public Type getType() {
        return Type.OPERAND;
    }
}

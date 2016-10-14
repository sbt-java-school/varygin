package lesson20.home.converter.values.bigdecimals;

import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

class NumberToBigDecimalConverter implements ConvertValue<BigDecimal> {
    public BigDecimal convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return new BigDecimal(value.toString());
    }
}

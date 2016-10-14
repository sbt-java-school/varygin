package lesson20.home.converter.values.bigdecimals;

import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

class StringToBigDecimalConverter implements ConvertValue<BigDecimal> {
    @Override
    public BigDecimal convert(Object valueFrom) {
        String value = (String) valueFrom;
        return new BigDecimal(value);
    }
}

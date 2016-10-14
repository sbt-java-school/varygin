package lesson20.home.converter.values.integers;

import lesson20.home.converter.values.ConvertValue;

class NumberToIntegerConverter implements ConvertValue<Integer> {
    public Integer convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.intValue();
    }
}

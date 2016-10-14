package lesson20.home.converter.values.doubles;

import lesson20.home.converter.values.ConvertValue;

class NumberToDoubleConverter implements ConvertValue<Double> {
    public Double convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.doubleValue();
    }
}

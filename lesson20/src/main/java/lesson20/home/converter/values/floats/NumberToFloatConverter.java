package lesson20.home.converter.values.floats;

import lesson20.home.converter.values.ConvertValue;

class NumberToFloatConverter implements ConvertValue<Float> {
    public Float convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.floatValue();
    }
}

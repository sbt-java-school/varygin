package lesson20.home.converter.values.floats;

import lesson20.home.converter.values.ConvertValue;

class StringToFloatConverter implements ConvertValue<Float> {
    @Override
    public Float convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Float.parseFloat(value);
    }
}

package lesson20.home.converter.values.doubles;

import lesson20.home.converter.values.ConvertValue;

class StringToDoubleConverter implements ConvertValue<Double> {
    @Override
    public Double convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Double.parseDouble(value);
    }
}

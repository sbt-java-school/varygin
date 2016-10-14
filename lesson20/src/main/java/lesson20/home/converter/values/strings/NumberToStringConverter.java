package lesson20.home.converter.values.strings;

import lesson20.home.converter.values.ConvertValue;

class NumberToStringConverter implements ConvertValue<String> {
    public String convert(Object valueFrom) {
        return valueFrom.toString();
    }
}

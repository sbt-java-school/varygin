package lesson20.home.converter.values.strings;

import lesson20.home.converter.values.ConvertValue;

class StringToStringConverter implements ConvertValue<String> {
    @Override
    public String convert(Object valueFrom) {
        return (String) valueFrom;
    }
}

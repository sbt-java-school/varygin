package lesson20.home.converter.values.longs;

import lesson20.home.converter.values.ConvertValue;

class StringToLongConverter implements ConvertValue<Long> {
    @Override
    public Long convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Long.parseLong(value);
    }
}

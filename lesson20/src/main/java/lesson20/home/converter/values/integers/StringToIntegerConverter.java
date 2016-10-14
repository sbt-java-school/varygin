package lesson20.home.converter.values.integers;

import lesson20.home.converter.values.ConvertValue;

class StringToIntegerConverter implements ConvertValue<Integer> {
    @Override
    public Integer convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Integer.parseInt(value);
    }
}

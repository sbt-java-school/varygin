package lesson20.home.converter.values.longs;


import lesson20.home.converter.values.ConvertValue;

class NumberToLongConverter implements ConvertValue<Long> {
    @Override
    public Long convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.longValue();
    }
}

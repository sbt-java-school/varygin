package lesson20.home.converter.values.longs;

import lesson20.home.converter.values.AbstractConverterValue;
import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

public class ConverterValueLong
        extends AbstractConverterValue<Long>
        implements ConvertValue<Long> {

    protected void initDefaultConverters() {
        NumberToLongConverter convertValue = new NumberToLongConverter();
        addConverter(Long.class, convertValue);
        addConverter(Double.class, convertValue);
        addConverter(Float.class, convertValue);
        addConverter(Integer.class, convertValue);
        addConverter(BigDecimal.class, convertValue);

        addConverter(String.class, new StringToLongConverter());
    }

    @Override
    public Long convert(Object valueFrom) {
        ConvertValue<Long> convertValue = converters.get(valueFrom.getClass());
        return convertValue.convert(valueFrom);
    }
}

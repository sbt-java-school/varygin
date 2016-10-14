package lesson20.home.converter.values.integers;

import lesson20.home.converter.values.AbstractConverterValue;
import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

public class ConvertValueInteger extends AbstractConverterValue<Integer>
        implements ConvertValue<Integer> {

    protected void initDefaultConverters() {
        NumberToIntegerConverter convertValue = new NumberToIntegerConverter();
        addConverter(Long.class, convertValue);
        addConverter(Double.class, convertValue);
        addConverter(Float.class, convertValue);
        addConverter(Integer.class, convertValue);
        addConverter(BigDecimal.class, convertValue);

        addConverter(String.class, new StringToIntegerConverter());
    }

    @Override
    public Integer convert(Object valueFrom) {
        ConvertValue<Integer> convertValue = converters.get(valueFrom.getClass());
        return convertValue.convert(valueFrom);
    }
}

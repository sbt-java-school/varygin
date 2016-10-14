package lesson20.home.converter.values.strings;

import lesson20.home.converter.values.AbstractConverterValue;
import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

public class ConvertValueString extends AbstractConverterValue<String>
        implements ConvertValue<String> {

    protected void initDefaultConverters() {
        NumberToStringConverter convertValue = new NumberToStringConverter();
        addConverter(Long.class, convertValue);
        addConverter(Double.class, convertValue);
        addConverter(Float.class, convertValue);
        addConverter(Integer.class, convertValue);
        addConverter(BigDecimal.class, convertValue);

        addConverter(String.class, new StringToStringConverter());
    }

    @Override
    public String convert(Object valueFrom) {
        ConvertValue<String> convertValue = converters.get(valueFrom.getClass());
        return convertValue.convert(valueFrom);
    }
}

package lesson20.home.converter.values.doubles;

import lesson20.home.converter.values.AbstractConverterValue;
import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

public class ConvertValueDouble extends AbstractConverterValue<Double>
        implements ConvertValue<Double> {

    protected void initDefaultConverters() {
        NumberToDoubleConverter convertValue = new NumberToDoubleConverter();
        addConverter(Long.class, convertValue);
        addConverter(Double.class, convertValue);
        addConverter(Float.class, convertValue);
        addConverter(Integer.class, convertValue);
        addConverter(BigDecimal.class, convertValue);

        addConverter(String.class, new StringToDoubleConverter());
    }

    @Override
    public Double convert(Object valueFrom) {
        ConvertValue<Double> convertValue = converters.get(valueFrom.getClass());
        return convertValue.convert(valueFrom);
    }
}

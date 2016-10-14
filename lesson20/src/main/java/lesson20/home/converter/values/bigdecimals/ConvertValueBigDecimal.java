package lesson20.home.converter.values.bigdecimals;

import lesson20.home.converter.values.AbstractConverterValue;
import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

public class ConvertValueBigDecimal extends AbstractConverterValue<BigDecimal>
        implements ConvertValue<BigDecimal> {

    protected void initDefaultConverters() {
        NumberToBigDecimalConverter convertValue = new NumberToBigDecimalConverter();
        addConverter(Long.class, convertValue);
        addConverter(Double.class, convertValue);
        addConverter(Float.class, convertValue);
        addConverter(Integer.class, convertValue);
        addConverter(BigDecimal.class, convertValue);

        addConverter(String.class, new StringToBigDecimalConverter());
    }

    @Override
    public BigDecimal convert(Object valueFrom) {
        ConvertValue<BigDecimal> convertValue = converters.get(valueFrom.getClass());
        return convertValue.convert(valueFrom);
    }
}

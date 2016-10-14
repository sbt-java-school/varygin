package lesson20.home.converter.values.floats;

import lesson20.home.converter.values.AbstractConverterValue;
import lesson20.home.converter.values.ConvertValue;

import java.math.BigDecimal;

public class ConvertValueFloat extends AbstractConverterValue<Float>
        implements ConvertValue<Float> {

    protected void initDefaultConverters() {
        NumberToFloatConverter convertValue = new NumberToFloatConverter();
        addConverter(Long.class, convertValue);
        addConverter(Double.class, convertValue);
        addConverter(Float.class, convertValue);
        addConverter(Integer.class, convertValue);
        addConverter(BigDecimal.class, convertValue);

        addConverter(String.class, new StringToFloatConverter());
    }

    @Override
    public Float convert(Object valueFrom) {
        ConvertValue<Float> convertValue = converters.get(valueFrom.getClass());
        return convertValue.convert(valueFrom);
    }
}

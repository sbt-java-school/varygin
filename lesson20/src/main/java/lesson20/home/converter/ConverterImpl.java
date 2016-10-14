package lesson20.home.converter;

import lesson20.home.converter.values.ConvertValue;
import lesson20.home.converter.values.bigdecimals.ConvertValueBigDecimal;
import lesson20.home.converter.values.doubles.ConvertValueDouble;
import lesson20.home.converter.values.floats.ConvertValueFloat;
import lesson20.home.converter.values.integers.ConvertValueInteger;
import lesson20.home.converter.values.longs.ConverterValueLong;
import lesson20.home.converter.values.strings.ConvertValueString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса конвертера
 */
public class ConverterImpl implements Converter {
    private static final Map<Class, ConvertValue> converters = new HashMap<>();

    /**
     * Инициализация доступных классов в которые можно конвертировать
     */
    public ConverterImpl() {
        initDefaultConverters();
    }

    private void initDefaultConverters() {
        addConverterTo(BigDecimal.class, new ConvertValueBigDecimal());
        addConverterTo(Double.class, new ConvertValueDouble());
        addConverterTo(Float.class, new ConvertValueFloat());
        addConverterTo(Integer.class, new ConvertValueInteger());
        addConverterTo(Long.class, new ConverterValueLong());
        addConverterTo(String.class, new ConvertValueString());
    }

    public <T> T convert(Object valueFrom, Class<T> resultClass) {
        ConvertValue<T> convertValue = converters.get(resultClass);
        return convertValue.convert(valueFrom);
    }


    @Override
    public <T> void addConverterTo(Class<T> resultClass, ConvertValue convertValue) {
        converters.put(resultClass, convertValue);
    }

    @Override
    public <T> void removeConverterTo(Class<T> resultClass) {
        converters.remove(resultClass);
    }

    @Override
    public <T, P> void addConverterFrom(Class<T> resultClass, Class<P> classFrom, ConvertValue convertValue) {
        if (converters.containsKey(resultClass)) {
            ConvertValue convertValues = converters.get(resultClass);
            convertValues.addConverter(classFrom, convertValue);
        } else {
            throw new IllegalStateException("Instance of " + classFrom + " doesn't exists");
        }
    }

    @Override
    public <T, P> void removeConverterFrom(Class<T> resultClass, Class<P> classFrom) {
        if (converters.containsKey(resultClass)) {
            ConvertValue convertValues = converters.get(resultClass);
            convertValues.removeConverter(classFrom);
        } else {
            throw new IllegalStateException("Instance of " + classFrom + " doesn't exists");
        }
    }
}

package lesson20.home.converter.values;

import java.util.HashMap;
import java.util.Map;

/**
 * Частичная реализация интерфейса конвертации исходного значения в результирующее,
 * так как во всех наследниках должны быть методы по добавлению новых конвертеров
 */
public abstract class AbstractConverterValue<T> implements ConvertValue<T> {
    protected final Map<Class, ConvertValue> converters = new HashMap<>();

    public AbstractConverterValue() {
        initDefaultConverters();
    }

    @Override
    public <T> void addConverter(Class<T> classFrom, ConvertValue convertValue) {
        converters.put(classFrom, convertValue);
    }

    @Override
    public <T> void removeConverter(Class<T> classFrom) {
        converters.remove(classFrom);
    }

    protected abstract void initDefaultConverters();
}

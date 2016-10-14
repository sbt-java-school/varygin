package lesson20.home.converter.values;

/**
 * Интерфейс для непосредственной конвертации исходного значения в результирующее
 * Во всех пакетах текущего пакета содержатся стандартные для конвертера обработчики
 * конвертации.
 *
 * @param <T> тип возвращаемого значения
 */
public interface ConvertValue<T> {
    T convert(Object valueFrom);

    default <T> void addConverter(Class<T> classFrom, ConvertValue convertValue) {
        throw new IllegalStateException();
    }

    default <T> void removeConverter(Class<T> classFrom) {
        throw new IllegalStateException();
    }
}

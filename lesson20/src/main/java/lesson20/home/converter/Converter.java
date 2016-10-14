package lesson20.home.converter;


import lesson20.home.converter.values.ConvertValue;

/**
 * Интерфейс конвертера
 * <p>
 * В valueFrom приходит некоторое значение, которое нужно конвертировать
 * в значение с типом <code>resultClass</code> (к примеру Long из Integer)
 */
public interface Converter {
    /**
     * @param valueFrom:  объект типа Integer, Long, Float, Double, BigDecimal или String
     * @param resultClass класс объекта, в который необходима конвертация (любой из типов valueFrom)
     * @param <T>         тип возвращаемого объекта
     * @return объект типа <code>resultClass</code>
     */
    <T> T convert(Object valueFrom, Class<T> resultClass);

    /**
     * Добавление обработчика конвертации для результирующего типа
     *
     * @param resultClass  класс в который будет конвертироваться объект
     * @param convertValue экземпляр класса конвертации соответствующего типа
     * @param <T>          тип результирующего объекта
     */
    <T> void addConverterTo(Class<T> resultClass, ConvertValue convertValue);

    /**
     * Удаление обработчика конвертации
     *
     * @param resultClass класс объекта
     * @param <T>         тип класса объекта
     */
    <T> void removeConverterTo(Class<T> resultClass);

    /**
     * Реализует возможность добавления обработчика конвертации
     * из которого будет происходить непосредственная конвертация входного объекта
     *
     * @param resultClass  класс результирующего объекта
     * @param classFrom    класс объекта из которого будет происходить конвертация
     * @param convertValue экземпляр класса конвертации входного объекта
     * @param <T>          тип класса результирующего объекта
     * @param <P>          тип класса исходного объекта
     */
    <T, P> void addConverterFrom(Class<T> resultClass, Class<P> classFrom, ConvertValue convertValue);

    /**
     * Удаление обработчика конвертации
     *
     * @param resultClass класс результирующего объекта
     * @param classFrom   класс объекта из которого будет происходить конвертация
     * @param <T>         тип класса результирующего объекта
     * @param <P>          тип класса исходного объекта
     */
    <T, P> void removeConverterFrom(Class<T> resultClass, Class<P> classFrom);
}

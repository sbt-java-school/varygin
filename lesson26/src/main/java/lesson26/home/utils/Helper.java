package lesson26.home.utils;

import java.util.concurrent.Callable;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Класс объединяет в себе общие функции
 * для разных пакетов приложения
 */
public class Helper {
    /**
     * Метод получения расширения файлов. Предназначен для распознавания
     * ресширения загружаемых картинок/
     *
     * @param contentType mime-type картинки
     * @return расширение картинки
     */
    public static String getImageExtension(String contentType) {
        return substringBefore(substringAfter(contentType, "/"), ";");
    }

    /**
     * Обёртка для единообразной обработки ошибок сервисных методов
     *
     * @param action вызываемая функция
     * @param <V>    тип возвращаемого значения вызываемой функции
     * @return обект типа V
     */
    public static <V> V wrap(Callable<V> action) {
        try {
            return action.call();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Метод обработки ошибок при выполнении
     * обработчиков ajax запросов
     *
     * @param action вызываемая функция
     * @param <V>    тип возвращаемого функциуй значения
     * @return объект класса ResponseValue, содержащий результат выполнения ajax запроса:
     * success: 1 - запрос выполнен успешно, 0 - иначе
     * value: результат выполнения вызываемой функции
     * errors: текст ошибок при выполнении вызываемой функции
     */
    public static <V> ResponseValue action(Callable<V> action) {
        try {
            return new ResponseValue("1", action.call(), null);
        } catch (BusinessException e) {
            return new ResponseValue("0", null, join(e.getErrors(), "<br />"));
        } catch (Exception e) {
            return new ResponseValue("0", null, e.getMessage());
        }
    }

}

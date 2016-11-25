package lesson26.home.service;

import lesson26.home.service.core.ImageLoaderImpl;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис сохранения / удаления изображений рецептов
 * @see ImageLoaderImpl
 */
public interface ImageLoader {
    /**
     * Получение пути к новой картинке на сервере
     *
     * @param image экземпляр объекта картинки,
     *              загруженной на сервер во временную директорию
     * @return путь до места хранения новой картинки на сервере
     */
    String getPath(MultipartFile image);

    /**
     * Сохранение новой картинки на сервере
     *
     * @param image экземпляр объекта картинки,
     *              загруженной на сервер во временную директорию
     * @param path  путь сохранения картинки
     */
    void saveImage(MultipartFile image, String path);

    /**
     * Удаление картинки с сервера
     *
     * @param image имя картинки на сервере
     * @return результат выполнения операции: 1 - успешно, 0 - иначе
     */
    boolean remove(String image);
}

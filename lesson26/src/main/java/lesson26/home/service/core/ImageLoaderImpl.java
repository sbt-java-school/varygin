package lesson26.home.service.core;

import lesson26.home.service.ImageLoader;
import lesson26.home.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

import static lesson26.home.utils.Helper.getImageExtension;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

@Service
public class ImageLoaderImpl implements ImageLoader {
    private String staticPath;

    @Autowired
    public ImageLoaderImpl(String staticPath) {
        this.staticPath = staticPath;
    }


    @Override
    public String getPath(MultipartFile image) {
        isValid(image);
        initStoreFolder();

        return getFileName(image);
    }

    @Override
    public void saveImage(MultipartFile image, String path) {
        try {
            image.transferTo(new File(staticPath + path));
        } catch (Exception e) {
            throw new BusinessException("Ошибка при сохранении файла на сервере", e);
        }
    }

    @Override
    public boolean remove(String image) {
        File file = new File(staticPath + image);
        return file.exists() && file.delete();
    }

    /**
     * Метод генерирует уникальное имя файла, для сохранения на сервере
     * исходя из его имени, текущей метки времени и его расширения
     *
     * @param image файл изображения
     * @return имя файла для хренения на сервере
     */
    private String getFileName(MultipartFile image) {
        String orgName = image.getOriginalFilename();
        String extension = getImageExtension(image.getContentType());


        return md5Hex(orgName + new Date().getTime())
                + "."
                + extension;
    }

    /**
     * Проверка файла на валидность:
     * Файл может иметь формат jpeg (jpg) или png
     *
     * @param image файл изображения
     */
    private void isValid(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new BusinessException("Картинка не найдена");
        }

        String extension = getImageExtension(image.getContentType()).toLowerCase();
        if (!"png".equals(extension) &&
                !"jpeg".equals(extension) &&
                !"jpg".equals(extension)) {
            throw new BusinessException("Доступные форматы файлов: jpeg и png. " +
                    "Формат файла: " + extension);
        }
    }

    /**
     * Проверка наличия директории для хранения файлов на сервере
     * Если директория отсутствует, приложение пытается её создать
     */
    private void initStoreFolder() {
        if (!new File(staticPath).exists() &&
                !new File(staticPath).mkdir()) {
            throw new BusinessException("Невозможно создать директорию " +
                    "для сохранения файла");
        }
    }
}

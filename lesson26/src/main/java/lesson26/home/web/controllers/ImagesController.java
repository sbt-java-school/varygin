package lesson26.home.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Контроллер для мапинга картинок рецептов
 */
@Controller
@RequestMapping("/images")
public class ImagesController {
    private String staticPath;

    @Autowired
    public ImagesController(String staticPath) {
        this.staticPath = staticPath;
    }

    /**
     * Метод для обработки запросов на вывод картинок приложения
     * Если картинка не найдена на сервера, то возвращается
     * стандартная картинка приложения
     *
     * @param filename  имя файла картинки
     * @param extension расширение файла картинки
     * @param request   объект реквест запроса
     * @param response  объект ответа
     * @throws IOException проброс ошибок ввода / вывода
     */
    @RequestMapping("/{filename}.{extension}")
    public void getImage(@PathVariable(value = "filename", required = false) String filename,
                         @PathVariable(value = "extension", required = false) String extension,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        File file = null;
        if (!isBlank(filename) && !isBlank(extension)) {
            file = new File(staticPath, filename + "." + extension);
        }

        if (Objects.isNull(file) || !file.exists()) {
            String path = request.getServletContext()
                    .getRealPath("/resources/img/empty-image.png");
            file = new File(path);
        }

        response.setHeader("Content-Type", "image/gif");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
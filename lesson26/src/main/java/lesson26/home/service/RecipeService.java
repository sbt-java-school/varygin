package lesson26.home.service;

import lesson26.home.utils.Page;
import lesson26.home.service.core.RecipeServiceImpl;
import lesson26.home.service.entities.RecipeDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Интерфейс сервиса для взаимодействия между представлением и
 * объектом Recipe базы данных
 *
 * @see RecipeServiceImpl
 */
public interface RecipeService {
    /**
     * Валидация и сохранение / обновление введённых
     * пользователем данных рецепта
     *
     * @param recipe объект рецепта для сохранения
     * @return идентификатор нового / обновлённого рецепта
     */
    Long save(RecipeDto recipe);

    /**
     * Получение рецепта для передачи
     * в представление по идентификатору
     *
     * @param id идентификатор рецепта
     * @return объект рецепта для отображения в представлении
     */
    RecipeDto get(Long id);

    /**
     * Поиск рецептов по названию
     *
     * @param name часть или полное наименование рецепта
     * @return список рецептов, удовлетворяющих фильтру
     */
    Page<RecipeDto> getList(String name, Integer page);

    /**
     * Обработка запроса от пользователя на удаление рецепта
     *
     * @param recipeDto объект рецепта для удаления
     */
    boolean delete(RecipeDto recipeDto);

    /**
     * Сохранение / изменение картинки рецепта
     * @param recipeId идентификатор рецепта
     * @param image экземпляр загружаемой картинки
     */
    void changeImage(Long recipeId, MultipartFile image);
}

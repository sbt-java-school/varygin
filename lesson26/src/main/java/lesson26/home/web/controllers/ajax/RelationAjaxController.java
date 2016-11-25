package lesson26.home.web.controllers.ajax;

import lesson26.home.service.RelationService;
import lesson26.home.service.entities.RelationDto;
import lesson26.home.utils.Helper;
import lesson26.home.utils.ResponseValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

import static lesson26.home.utils.Helper.action;

@RestController
@RequestMapping("/ajax/relation")
public class RelationAjaxController {
    private RelationService relationService;

    @Autowired
    public RelationAjaxController(RelationService relationService) {
        this.relationService = relationService;
    }

    /**
     * Метод добавления ингредиента к рецепту
     *
     * @param relation объект, содержащий идентификаторы рецепта,
     *                 ингредиента и единицы измерения,
     *                 а также количество добавляемого ингредиента
     * @return json объект {@link Helper#action(Callable)}
     */
    @PostMapping("/add")
    public ResponseValue addIngredientToRecipe(@RequestBody RelationDto relation) {
        return action(() -> {
            return relationService.save(relation);
        });
    }

    /**
     * Метод удаления ингредиента из рецепта
     *
     * @param relation метод, содержащий идентификатор
     *                 элемента в связующей таблице рецептов и ингредиентов
     * @return json объект {@link Helper#action(Callable)}
     */
    @DeleteMapping("/remove")
    public ResponseValue removeIngredient(@RequestBody RelationDto relation) {
        return action(() -> relationService.delete(relation));
    }
}

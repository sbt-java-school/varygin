package lesson26.home.web.controllers.ajax;

import lesson26.home.service.RecipeService;
import lesson26.home.service.entities.RecipeDto;
import lesson26.home.utils.Helper;
import lesson26.home.utils.ResponseValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

import static lesson26.home.utils.Helper.action;

@RestController
@RequestMapping("/ajax/recipe")
public class RecipeAjaxController {
    private RecipeService recipeService;

    @Autowired
    public RecipeAjaxController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Метод обработки формы добавления ингредиента
     *
     * @param recipeDto объект с введёнными пользователем
     *                  параметрами нового рецепта
     * @return json объект {@link Helper#action(Callable)}
     */
    @PostMapping("/add")
    public ResponseValue addRecipe(@RequestBody RecipeDto recipeDto) {
        return action(() -> recipeService.save(recipeDto));
    }

    /**
     * Удаление рецепта
     *
     * @param recipeDto объект, содержащий идентификатор удаляемого рецепта
     * @return json объект {@link Helper#action(Callable)}
     */
    @DeleteMapping("/remove")
    public ResponseValue removeRecipe(@RequestBody RecipeDto recipeDto) {
        return action(() -> recipeService.delete(recipeDto));
    }

    /**
     * Метод удаления картинки рецепта
     *
     * @param recipeDto объект, содержащий идентификатор удаляемого рецепта
     * @return json объект {@link Helper#action(Callable)}
     */
    @DeleteMapping("image/remove")
    public ResponseValue removeRecipeImage(@RequestBody RecipeDto recipeDto) {
        return action(() -> {
            recipeService.changeImage(recipeDto.getId(), null);
            return "ok";
        });
    }
}

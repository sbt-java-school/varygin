package lesson26.home.web.controllers.ajax;

import lesson26.home.service.IngredientService;
import lesson26.home.service.entities.IngredientDto;
import lesson26.home.utils.Helper;
import lesson26.home.utils.Page;
import lesson26.home.utils.ResponseValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.concurrent.Callable;

import static lesson26.home.utils.Helper.action;

@RestController
@RequestMapping("/ajax/ingredient")
public class IngredientAjaxController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientAjaxController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Метод получения списка ингредиентов
     *
     * @return html со списком ингредиентов
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        HashMap<String, Object> model = new HashMap<>();
        Page<IngredientDto> list = ingredientService.getList("", page);
        model.put("ingredients", list.getContents());
        model.put("total", list.getTotalCount());
        model.put("page", page);
        return new ModelAndView("ingredient/list", model);
    }

    @PostMapping("/filter")
    public ResponseValue filter(@RequestParam(value = "query", defaultValue = "") String query) {
        return action(() -> ingredientService.getList(query, 0).getContents());
    }

    /**
     * Метод добавления нового ингредиента
     *
     * @param ingredientDto объект с введёнными пользователем
     *                      параметрами нового ингредиента
     * @return json объект  {@link Helper#action(Callable)}
     */
    @PostMapping("/add")
    public ResponseValue addIngredient(@RequestBody IngredientDto ingredientDto) {
        return action(() -> ingredientService.save(ingredientDto));
    }

    /**
     * Удаление ингредиента
     *
     * @param ingredientDto объект, содержащий идентификатор
     *                      удаляемого ингредиента
     * @return json объект {@link Helper#action(Callable)}
     */
    @DeleteMapping("/remove")
    public ResponseValue removeIngredient(@RequestBody IngredientDto ingredientDto) {
        return action(() -> ingredientService.delete(ingredientDto));
    }
}

package lesson26.home.web.controllers;

import lesson26.home.service.IngredientService;
import lesson26.home.service.RecipeService;
import lesson26.home.service.UnitService;
import lesson26.home.service.schema.RecipeTdo;
import lesson26.home.service.schema.RelationTdo;
import lesson26.home.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitService unitService;

    @Autowired
    public RecipeController(RecipeService recipeService,
                            IngredientService ingredientService,
                            UnitService unitService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitService = unitService;
    }

    /**
     * Страница рецепта
     *
     * @param session текущая сессия
     * @param id      идентификатор рецепта
     * @param model   мапа для вывода переменных представления
     * @return строковое имя представления
     */
    @RequestMapping("/index/{id}")
    public String index(HttpSession session,
                        @PathVariable("id") Long id,
                        ModelMap model) {
        try {
            RecipeTdo recipe = recipeService.get(id);

            model.addAttribute("title", recipe.getName());
            model.addAttribute("recipe", recipe);
            prepareIngredientForm(session, id, model);

            return "recipe/index";
        } catch (BusinessException e) {
            return "redirect:/";
        }
    }

    /**
     * Страница добавления ингредиента
     *
     * @param model мапа для вывода переменных представления
     * @return строковое имя представления
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String recipeForm(ModelMap model) {
        model.put("recipe", new RecipeTdo());
        model.put("title", "Добавление рецепта");

        return "recipe/add-form";
    }

    /**
     * Метод для подготовки данных формы добавления рецепта
     *
     * @param session  текущая сессия
     * @param recipeId идентификатор рецепта
     * @param model    мапа для вывода переменных представления
     */
    private void prepareIngredientForm(HttpSession session,
                                       Long recipeId,
                                       ModelMap model) {
        RelationTdo relation = (RelationTdo) session.getAttribute("relation");
        model.put("relation", relation == null ? new RelationTdo() : relation);
        model.put("errors", session.getAttribute("errors"));
        model.put("recipeId", recipeId);
        model.put("ingredients", ingredientService.getList());
        model.put("units", unitService.getList());

        session.removeAttribute("errors");
        session.removeAttribute("relation");
    }

}

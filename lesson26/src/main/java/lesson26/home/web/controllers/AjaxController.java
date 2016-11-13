package lesson26.home.web.controllers;

import lesson26.home.service.RecipeService;
import lesson26.home.service.RelationService;
import lesson26.home.service.schema.RecipeTdo;
import lesson26.home.service.schema.RelationTdo;
import lesson26.home.utils.AjaxCall;
import lesson26.home.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

import static org.apache.commons.lang.StringUtils.join;

@Controller
@ResponseBody
@RequestMapping("/ajax")
public class AjaxController {
    private RecipeService recipeService;
    private RelationService relationService;

    @Autowired
    public AjaxController(RecipeService recipeService,
                          RelationService relationService) {
        this.recipeService = recipeService;
        this.relationService = relationService;
    }

    /**
     * Метод обработки формы добавления ингредиента
     *
     * @param recipeTdo введённые пользователем параметры
     * @return json
     */
    @RequestMapping(value = "/add/recipe", method = RequestMethod.POST)
    public ResponseValue addRecipe(@RequestBody RecipeTdo recipeTdo) {
        return action(() -> recipeService.save(recipeTdo));
    }

    @RequestMapping(value = "/remove/recipe", method = RequestMethod.POST)
    public ResponseValue removeRecipe(@RequestBody RecipeTdo recipeTdo) {
        return action(() -> {
            recipeService.delete(recipeTdo);
            return "ok";
        });
    }

    @RequestMapping(value = "/add/recipe/ingredient", method = RequestMethod.POST)
    public ResponseValue addIngredientToRecipe(@RequestBody RelationTdo relation) {
        return action(() -> {
            Long id = relationService.save(relation);
            return relationService.get(id);
        });
    }

    @RequestMapping(value = "/remove/ingredient", method = RequestMethod.POST)
    public ResponseValue removeIngredient(@RequestBody RelationTdo relation) {
        return action(() -> {
            relationService.delete(relation);
            return "ok";
        });
    }

    private <V> ResponseValue action(AjaxCall<V> action) {
        try {
            return new ResponseValue("1", action.call(), null);
        } catch (BusinessException e) {
            return new ResponseValue("0", null, join(e.getErrors(), "<br />"));
        }
    }

    private static class ResponseValue implements Serializable {
        private final String success;
        private final Object value;
        private final String errors;

        private ResponseValue(String success, Object value, String errors) {
            this.success = success;
            this.value = value;
            this.errors = errors;
        }

        public String getSuccess() {
            return success;
        }

        public Object getValue() {
            return value;
        }

        public String getErrors() {
            return errors;
        }
    }
}

package lesson26.home.web.controllers;

import lesson26.home.service.RecipeService;
import lesson26.home.service.schema.RecipeTdo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class IndexController {

    private RecipeService recipeService;

    @Autowired
    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/", method = {POST, GET})
    public String index(
            @RequestParam(value = "query", defaultValue = "") String query,
            ModelMap model) {
        List<RecipeTdo> recipes = recipeService.getByName(query);
        model.put("recipes", recipes);
        model.put("query", query == null ? "" : query);
        model.put("title", "Рецепты");
        return "index";
    }
}

package lesson26.home.web.controllers;

import lesson26.home.service.RecipeService;
import lesson26.home.service.entities.RecipeDto;
import lesson26.home.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Контроллер главной страницы сайта
 */
@Controller
public class IndexController {

    private RecipeService recipeService;

    @Autowired
    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Метод для выборки всех рецептов, либо с фильтром по названию
     *
     * @param model объект выводимой модели
     * @return строка - название представления модели
     */
    @RequestMapping(value = "/", method = GET)
    public String index(ModelMap model) {
        _getRecipes(model, "", 1);
        return "index";
    }

    /**
     * Метод поиска рецептов по подстроке
     *
     * @param query искомая подстрока
     * @param page  текущая страница
     * @param model карта переменных страницы
     * @return строка - название выводимой модели
     */
    @RequestMapping(value = "/search", method = GET)
    public String search(@RequestParam(value = "query", defaultValue = "") String query,
                         @RequestParam(value = "p", defaultValue = "1") Integer page,
                         ModelMap model) {
        _getRecipes(model, query, page);
        return "index";
    }

    private void _getRecipes(ModelMap model, String query, Integer page) {
        Page<RecipeDto> result = recipeService.getList(query, page - 1);
        List<RecipeDto> recipes = result.getContents();

        model.put("recipes", recipes);
        model.put("total", result.getTotalCount());
        model.put("query", query);
        model.put("pageUrl", _pageUrl(query));
        model.put("page", page);
        model.put("title", "Рецепты");
    }

    private String _pageUrl(String query) {
        StringBuilder url = new StringBuilder("/search").append("?");
        if (StringUtils.isNotBlank(query)) {
            url.append("query=").append(query).append("&");
        }
        url.append("p=");
        return url.toString();
    }
}

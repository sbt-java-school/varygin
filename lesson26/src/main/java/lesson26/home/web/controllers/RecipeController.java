package lesson26.home.web.controllers;

import lesson26.home.service.IngredientService;
import lesson26.home.service.RecipeService;
import lesson26.home.service.UnitService;
import lesson26.home.service.entities.RecipeDto;
import lesson26.home.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Контроллер страницы рецепта
 */
@Controller
@RequestMapping("/recipe")
public class RecipeController implements HandlerExceptionResolver {
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
            RecipeDto recipe = recipeService.get(id);

            model.addAttribute("title", recipe.getName());
            model.addAttribute("recipe", recipe);
            prepareIngredientForm(session, id, model);

            return "recipe/index";
        } catch (BusinessException e) {
            return "redirect:/";
        }
    }

    /**
     * Обработка запроса на сохранение картинки рецепта
     *
     * @param session текущая сессия
     * @param id      идентификатор рецепта
     * @param image   объект загружаемой картинки
     * @return строка - редирект на страницу рецепта
     */
    @RequestMapping(value = "/index/{id}/image", method = RequestMethod.POST)
    public String addImage(HttpSession session,
                           @PathVariable("id") Long id,
                           @RequestParam("image") MultipartFile image) {
        try {
            recipeService.changeImage(id, image);
        } catch (BusinessException e) {
            session.setAttribute("errors", e.getErrors());
        }

        return "redirect:/recipe/index/" + id;
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
                                       Map<String, Object> model) {
        model.put("errors", session.getAttribute("errors"));
        model.put("recipeId", recipeId);

        session.removeAttribute("errors");
    }

    /**
     * Метод обработки ошибок в процессе
     * загрузки картинок во временную папку на сервере
     *
     * @param request   объект запроса
     * @param response  объект ответа сервера
     * @param handler   обработчик ошибок
     * @param exception объект ошиибки
     * @return редирект на страницу рецепта, с которой была отправлена форма
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception exception) {
        String url = "";
        if (exception instanceof MaxUploadSizeExceededException) {
            request.getSession().setAttribute("errors", "Максимальный размер файла " +
                    "не должен превышать 3 мегабайта");
            url = request.getRequestURI().replace("/image", "");
        }
        return new ModelAndView("redirect:" + url);
    }
}

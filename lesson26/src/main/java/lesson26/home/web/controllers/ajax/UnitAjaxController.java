package lesson26.home.web.controllers.ajax;

import lesson26.home.service.UnitService;
import lesson26.home.service.entities.UnitDto;
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
@RequestMapping("/ajax/unit")
public class UnitAjaxController {
    private UnitService unitService;

    @Autowired
    public UnitAjaxController(UnitService unitService) {
        this.unitService = unitService;
    }

    /**
     * Метод получения списка ингредиентов
     *
     * @return html со списком ингредиентов
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        HashMap<String, Object> model = new HashMap<>();
        Page<UnitDto> list = unitService.getList("", page);
        model.put("units", list.getContents());
        model.put("total", list.getTotalCount());
        model.put("page", page);
        return new ModelAndView("unit/list", model);
    }

    @PostMapping("/filter")
    public ResponseValue filter(@RequestParam(value = "query", defaultValue = "") String query) {
        return action(() -> unitService.getList(query, 0).getContents());
    }

    /**
     * Метод добавления нового ингредиента
     *
     * @param unitDto объект с введёнными пользователем
     *                параметрами нового ингредиента
     * @return json объект  {@link Helper#action(Callable)}
     */
    @PostMapping("/add")
    public ResponseValue addUnit(@RequestBody UnitDto unitDto) {
        return action(() -> unitService.save(unitDto));
    }

    /**
     * Удаление ингредиента
     *
     * @param unitDto объект, содержащий идентификатор
     *                удаляемого ингредиента
     * @return json объект {@link Helper#action(Callable)}
     */
    @DeleteMapping("/remove")
    public ResponseValue removeUnit(@RequestBody UnitDto unitDto) {
        return action(() -> unitService.delete(unitDto));
    }
}

package lesson26.home.service.core;

import lesson26.home.dao.IngredientDao;
import lesson26.home.dao.schema.Ingredient;
import lesson26.home.service.IngredientService;
import lesson26.home.service.schema.IngredientTdo;
import lesson26.home.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Сервис для обработки ингредиентов
 * и сохранения / изменения / удаления их в базе данных
 */
@Service
public class IngredientServiceImpl implements IngredientService {
    private IngredientDao ingredientDao;

    @Autowired
    public IngredientServiceImpl(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @Override
    @Transactional
    public Map<Long, String> getList() {
        try {
            @SuppressWarnings("unchecked")
            List<Ingredient> list = ingredientDao.getList();
            return list.stream().map(IngredientTdo::new)
                    .collect(toMap(IngredientTdo::getId, IngredientTdo::getName));
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}

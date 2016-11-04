package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.IngredientsDao;
import lesson24.db.sсhema.Ingredient;
import lesson24.db.sсhema.Unit;
import lesson24.exceptions.BusinessException;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static lesson24.errors.ValidateMessages.*;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Сервис для обработки ингредиентов
 * и сохранения / изменения / удаления их в базе данных
 */
public class IngredientService {
    private static final int MAX_NAME_LENGTH = 50;
    private final String tmpAmount;
    private Double amount;
    private Ingredient ingredient;
    private Unit unit;

    /**
     * Конструктор для инициализации нового ингредиента
     *
     * @param name      название нового ингредиента
     * @param unit      единица измерения
     * @param tmpAmount строковое представление количества ингрединета
     */
    public IngredientService(String name, Unit unit, String tmpAmount) {
        this(unit, tmpAmount);
        this.ingredient = new Ingredient(name);
    }

    /**
     * Конструктор для инициализации существующего в базе ингредиента
     *
     * @param value     экземпляр существующего ингредиента
     * @param unit      единица измерения
     * @param tmpAmount строковое представление количества ингрединета
     */
    public IngredientService(Ingredient value, Unit unit, String tmpAmount) {
        this(unit, tmpAmount);
        this.ingredient = value;
    }

    /**
     * @param unit      единица измерения
     * @param tmpAmount строковое представление количества ингрединета
     */
    private IngredientService(Unit unit, String tmpAmount) {
        this.unit = unit;
        this.tmpAmount = tmpAmount;
    }

    /**
     * Метод пытается сохранить ингредиент в базе данных
     * и выбрасывает исключение BusinessException, в случае
     * возникновения ошибки.
     */
    public void save() {
        validate();

        try (DaoFactory factory = new DaoFactory()) {
            if (ingredient.getId() == null) {
                Long id = factory.get(IngredientsDao.class).create(ingredient);
                ingredient.setId(id);
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(INGREDIENT_EXIST);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Валидация данных перед сохранением в базу данных
     */
    private void validate() {
        if (isNull(ingredient) || !ingredient.isValid()) {
            throw new BusinessException(INGREDIENT_NOT_SELECTED);
        }
        if (ingredient.getName().length() > MAX_NAME_LENGTH) {
            throw new BusinessException(NAME_LENGTH_ERROR);
        }
        if (isNull(unit) || !unit.isValid()) {
            throw new BusinessException(UNIT_NOT_SELECTED);
        }
        try {
            if ((amount = Double.parseDouble(tmpAmount)) == 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new BusinessException(AMOUNT_FORMAT);
        }
    }

    /**
     * Удаление ингредиента по идентификатору
     */
    public void remove() {
        if (isNull(ingredient.getId())) {
            throw new BusinessException(INGREDIENT_NOT_SELECTED);
        }

        try (DaoFactory factory = new DaoFactory()) {
            if (!factory.get(IngredientsDao.class).remove(ingredient.getId())) {
                throw new BusinessException(INGREDIENT_REMOVE_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * Метод для получения списка всех ингредиентов
     *
     * @return список всех ингредиентов, находящихся в базе данных
     */
    public static List<Ingredient> getList() {
        try (DaoFactory factory = new DaoFactory()) {
            Model ingredientsDao = factory.get(IngredientsDao.class);
            List<?> ingredientsDaoList = ingredientsDao.getList();

            return ingredientsDaoList.stream()
                    .map(item -> (Ingredient) item)
                    .collect(toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public String toString() {
        StrBuilder tmp = new StrBuilder(ingredient.toString());
        if (isNotBlank(tmpAmount) && nonNull(unit)) {
            tmp.append(": ")
                    .append(tmpAmount)
                    .append(" ")
                    .append(unit.getShort_name());
        }
        return tmp.toString();
    }

    public Double getAmount() {
        return amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getTmpAmount() {
        return tmpAmount;
    }
}

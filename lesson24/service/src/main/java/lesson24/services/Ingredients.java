package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.IngredientsDao;
import lesson24.db.shema.Ingredient;
import lesson24.db.shema.Unit;
import lesson24.exceptions.BusinessException;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 *
 */
public class Ingredients {
    private Ingredient ingredient;

    private Unit unit;
    private Integer amount;
    private String tmpAmount;

    public Ingredients(String name, Unit unit, String tmpAmount) {
        this(unit, tmpAmount);
        this.ingredient = new Ingredient(name);
    }

    public Ingredients(Ingredient value, Unit unit, String tmpAmount) {
        this(unit, tmpAmount);
        this.ingredient = value;
    }

    private Ingredients(Unit unit, String tmpAmount) {
        this.unit = unit;
        this.tmpAmount = tmpAmount;
    }

    public Unit getUnit() {
        return unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void validate() {
        if (ingredient == null || isEmpty(ingredient.getName())) {
            throw new BusinessException("Выберите ингредиент");
        }
        if (unit == null) {
            throw new BusinessException("Выберите единицу изерения");
        }
        try {
            amount = Integer.parseInt(tmpAmount);
            if (amount == 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new BusinessException("Введите корректное количество ингредиента");
        }
    }

    public void save() {
        validate();
        try (DaoFactory factory = new DaoFactory()) {
            if (ingredient.getId() == null) {
                Long id = factory.create(IngredientsDao.class).create(ingredient);
                ingredient.setId(id);
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException("Ингредиент с таким названием уже существует");
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public void remove() {
        if (ingredient.getId() == null) {
            throw new BusinessException("Ингредиент не найден");
        }
        try (DaoFactory factory = new DaoFactory()) {
            if (!factory.create(IngredientsDao.class).remove(ingredient.getId())) {
                throw new BusinessException("Невозможно удалить ингредиент");
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public String toString() {
        StrBuilder tmp = new StrBuilder(ingredient.toString());
        if (amount != null && unit != null) {
            tmp.append(": ")
                    .append(amount.toString())
                    .append(" ")
                    .append(unit.getShort_name());
        }
        return tmp.toString();
    }

    public static List<Ingredient> getList() {
        try (DaoFactory factory = new DaoFactory()) {
            Model ingredientsDao = factory.create(IngredientsDao.class);
            Optional<List<?>> ingredientsDaoList = ingredientsDao.getList();
            if (!ingredientsDaoList.isPresent()) {
                return null;
            }

            return ingredientsDaoList.get()
                    .stream()
                    .map(item -> (Ingredient) item)
                    .collect(toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}

package lesson24.view.views.ingredient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.IngredientsDao;
import lesson24.db.components.UnitsDao;
import lesson24.db.dao.Ingredient;
import lesson24.db.dao.Unit;
import lesson24.view.Control;
import lesson24.view.ModalFactory;
import lesson24.view.views.exceptions.BusinessExceptions;
import lesson24.view.views.recipe.CreateRecipe;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class IngredientsController implements Control {
    private CreateRecipe control;
    private Stage stage;

    @FXML
    private TextField name;
    @FXML
    private TextField amount;
    @FXML
    private ChoiceBox<Ingredient> ingredients;
    @FXML
    private ChoiceBox<Unit> units;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    public IngredientsController() {
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParent(Control control) {
        this.control = (CreateRecipe) control;
    }

    @FXML
    private void initialize() {
        try (DaoFactory factory = new DaoFactory()){
            Model unitsDao = factory.create(UnitsDao.class);
            Optional<List<Object>> unitsDaoList = unitsDao.getList();
            if (unitsDaoList.isPresent()) {
                initUnitsList(unitsDaoList.get());
            }

            Model ingredientsDao = factory.create(IngredientsDao.class);
            Optional<List<Object>> ingredientsDaoList = ingredientsDao.getList();
            if (ingredientsDaoList.isPresent()) {
                initIngredientsList(ingredientsDaoList.get());
            }
        } catch (Exception e) {
            ModalFactory.error(stage, e.getMessage(), e.getLocalizedMessage());
        }
    }

    private void initIngredientsList(List<Object> objects) {
        List<Ingredient> items = objects.stream().map(item -> (Ingredient) item).collect(toList());
        ingredients.setItems(FXCollections.observableArrayList(items));
    }

    private void initUnitsList(List<Object> objects) {
        List<Unit> items = objects.stream().map(item -> (Unit) item).collect(toList());
        units.setItems(FXCollections.observableArrayList(items));
    }

    //TODO: создать транзакционность
    @FXML
    private void add() {
        try (DaoFactory factory = new DaoFactory()) {
            Ingredient ingredient = validate();
            if (ingredient.getId() == null) {
                Model ingredientsDao = factory.create(IngredientsDao.class);
                Long iId = ingredientsDao.create(ingredient);
                ingredient.setId(iId);
            }
            control.addIngredient(ingredient);
            stage.close();
        } catch (Exception e) {
            ModalFactory.error(stage, e.getMessage(), e.getLocalizedMessage());

        }
    }

    private Ingredient validate() {
        String textField = name.getText();
        Ingredient ingredient = null;
        if (textField.length() != 0) {
            ingredient = new Ingredient(textField);
        } else if (ingredients.getValue() != null) {
            ingredient = ingredients.getValue();
        }
        if (ingredient == null) {
            throw new BusinessExceptions("Выберите ингредиент");
        }
        Unit unit = units.getValue();
        if (unit == null) {
            throw new BusinessExceptions("Выберите единицу изерения");
        }
        ingredient.setUnit(unit);
        String text = amount.getText();
        try {
            int val = Integer.parseInt(text);
            if (val == 0) {
                throw new NumberFormatException();
            }
            ingredient.setAmount(val);
        } catch (NumberFormatException e) {
            throw new BusinessExceptions("Введите корректное количество ингредиента");
        }
        return ingredient;
    }

    @FXML
    private void cancel() {
        stage.close();
    }
}

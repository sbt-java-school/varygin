package lesson24.view.views.ingredient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lesson24.db.sсhema.Ingredient;
import lesson24.db.sсhema.Unit;
import lesson24.services.IngredientService;
import lesson24.services.UnitService;
import lesson24.view.views.Control;
import lesson24.view.views.ModalFactory;
import lesson24.view.views.recipe.CreateRecipe;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Контроллер добавления ингредиентов в рецепт
 */
public class AddIngredients implements Control {
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

    public AddIngredients() {
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
        ModalFactory.wrap(() -> {
            units.setItems(FXCollections.observableArrayList(UnitService.getList()));
            ingredients.setItems(FXCollections.observableArrayList(IngredientService.getList()));
        }, stage);
    }

    @FXML
    private void add() {
        ModalFactory.wrap(() -> {
            IngredientService ingredientService;
            if (!isEmpty(name.getText())) {
                ingredientService = new IngredientService(name.getText(), units.getValue(), amount.getText());
            } else {
                ingredientService = new IngredientService(ingredients.getValue(), units.getValue(), amount.getText());
            }
            ingredientService.save();
            control.addIngredient(ingredientService);
            cancel();
        }, stage);
    }

    @FXML
    private void cancel() {
        stage.close();
    }
}

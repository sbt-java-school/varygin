package lesson24.view.views.ingredient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lesson24.db.shema.Ingredient;
import lesson24.db.shema.Unit;
import lesson24.exceptions.BusinessException;
import lesson24.services.Ingredients;
import lesson24.services.Units;
import lesson24.view.Control;
import lesson24.view.ModalFactory;
import lesson24.view.views.recipe.CreateRecipe;

import static org.apache.commons.lang.StringUtils.isEmpty;

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
        try {
            units.setItems(FXCollections.observableArrayList(Units.getList()));
            ingredients.setItems(FXCollections.observableArrayList(Ingredients.getList()));
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }

    @FXML
    private void add() {
        try {
            Ingredients ingredientService;
            if (!isEmpty(name.getText())) {
                ingredientService = new Ingredients(name.getText(), units.getValue(), amount.getText());
            } else {
                ingredientService = new Ingredients(ingredients.getValue(), units.getValue(), amount.getText());
            }
            ingredientService.save();
            control.addIngredient(ingredientService);
            cancel();
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }

    @FXML
    private void cancel() {
        stage.close();
    }
}

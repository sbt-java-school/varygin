package lesson24.view.views.recipe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lesson24.db.dao.Ingredient;
import lesson24.db.dao.Recipe;
import lesson24.view.Control;
import lesson24.view.ModalFactory;
import lesson24.view.views.home.HomePage;

import java.util.List;

public class CreateRecipe implements Control {
    private Stage stage;
    private HomePage control;
    private Recipe recipe;

    @FXML
    private TextField recipeName;
    @FXML
    private TextArea recipeDescription;
    @FXML
    private Button addIngredientsButton;
    @FXML
    private Button saveRecipeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ListView<Ingredient> ingredients;

    public CreateRecipe() {
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.getItems().add(ingredient);
    }

    @Override
    public void setParent(Control control) {
        this.control = (HomePage) control;
    }

    @FXML
    private void initialize() {
        this.recipe = new Recipe();
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void save() {
//        ObservableList<String> items = ingredients.getItems();
    }

    @FXML
    private void addIngredients() {
        ModalFactory.create(
                getClass().getResource("../ingredient/add.fxml"),
                "Добавление ингредиента",
                stage,
                this
        );
        recipe.setIngredients(ingredients.getItems());
    }
}

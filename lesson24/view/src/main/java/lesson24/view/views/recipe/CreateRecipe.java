package lesson24.view.views.recipe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lesson24.dao.Recipe;
import lesson24.exceptions.BusinessException;
import lesson24.services.Ingredients;
import lesson24.services.Recipes;
import lesson24.view.Control;
import lesson24.view.ModalFactory;
import lesson24.view.views.home.HomePage;

public class CreateRecipe implements Control {
    private Stage stage;
    private HomePage control;
    private Recipes recipeService;

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
    private ListView<Ingredients> ingredients;

    public CreateRecipe() {
    }

    public void edit(Recipe recipe) {
        recipeService = new Recipes(recipe);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addIngredient(Ingredients ingredient) {
        ingredients.getItems().add(ingredient);
    }

    @Override
    public void setParent(Control control) {
        this.control = (HomePage) control;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void save() {
        try {
            if (recipeService == null) {
                recipeService = new Recipes(recipeName.getText(), recipeDescription.getText());
            } else {
                recipeService.setFields(recipeName.getText(), recipeDescription.getText());
            }
            recipeService.setIngredients(ingredients.getItems());
            recipeService.save();
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }

    @FXML
    private void addIngredients() {
        try {
            ModalFactory.create(
                    getClass().getResource("../ingredient/add.fxml"),
                    "Добавление ингредиента",
                    stage,
                    this
            );
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }
}

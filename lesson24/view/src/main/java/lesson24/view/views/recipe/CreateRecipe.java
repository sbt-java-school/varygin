package lesson24.view.views.recipe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lesson24.db.shema.Recipe;
import lesson24.exceptions.BusinessException;
import lesson24.services.Ingredients;
import lesson24.services.Recipes;
import lesson24.services.RecipesToIngredients;
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
    private Button removeIngredientButton;
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
        ObservableList<Ingredients> items = ingredients.getItems();
        boolean result = items.stream().anyMatch(item ->
                item.getIngredient().getName().equals(ingredient.getIngredient().getName()));
        if (result) {
            throw new BusinessException("Вы уже добавили этот ингредиент");
        }
        items.add(ingredient);
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
            cancel();
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

    @FXML
    private void removeIngredients() {
        try {
            if (ingredients.getItems().isEmpty()) {
                throw new BusinessException("Список ингредиентов пуст");
            }
            ObservableList<Ingredients> selectedItems = ingredients.getSelectionModel().getSelectedItems();
            if (selectedItems.isEmpty()) {
                throw new BusinessException("Не выбрано ни одного ингредиента");
            }
            ModalFactory.confirm(
                    stage,
                    "Удалить только из списка рецепта? " +
                            "При выборе 'Нет' ингредиент будет удалён из базы данных.",
                    () -> {
                        selectedItems.forEach(RecipesToIngredients::removeByIngredient);
                        ingredients.getItems().removeAll(selectedItems);
                    }, () -> {
                        selectedItems.forEach(RecipesToIngredients::removeByIngredient);
                        selectedItems.forEach(Ingredients::remove);
                        ingredients.getItems().removeAll(selectedItems);
                    }
            );
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }
}

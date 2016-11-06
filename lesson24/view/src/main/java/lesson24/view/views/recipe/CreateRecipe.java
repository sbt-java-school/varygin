package lesson24.view.views.recipe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lesson24.db.sсhema.Recipe;
import lesson24.exceptions.BusinessException;
import lesson24.services.IngredientService;
import lesson24.services.RecipeService;
import lesson24.services.RelationService;
import lesson24.view.views.Control;
import lesson24.view.views.ModalFactory;

/**
 * Контроллер добавления рецепта
 */
public class CreateRecipe implements Control {
    private Stage stage;
    private Control control;
    private RecipeService recipeService;

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
    private ListView<IngredientService> ingredients;

    public CreateRecipe() {
    }

    public void editRecipe(Recipe recipe, ListView<IngredientService> ingredients) {
        recipeService = new RecipeService(recipe);
        recipeName.setText(recipe.getName());
        recipeDescription.setText(recipe.getDescription());
        this.ingredients.setItems(ingredients.getItems());
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParent(Control control) {
        this.control = control;
    }

    public void addIngredient(IngredientService ingredient) {
        ObservableList<IngredientService> items = ingredients.getItems();
        boolean result = items.stream().anyMatch(item ->
                item.getIngredient()
                        .getName()
                        .equals(ingredient.getIngredient().getName()));
        if (result) {
            throw new BusinessException("Вы уже добавили этот ингредиент");
        }
        items.add(ingredient);
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
        ModalFactory.wrap(() -> {
            if (recipeService == null) {
                recipeService = new RecipeService(recipeName.getText(),
                        recipeDescription.getText());
            } else {
                recipeService.setFields(recipeName.getText(),
                        recipeDescription.getText());
            }
            recipeService.setIngredients(ingredients.getItems());
            recipeService.save();
            cancel();
        }, stage);
    }

    @FXML
    private void addIngredients() {
        ModalFactory.create("views\\add.fxml",
                "Добавление ингредиента", stage, controller -> {
                    controller.setParent(this);
                });
    }

    @FXML
    private void removeIngredients() {
        ModalFactory.wrap(() -> {
            if (ingredients.getItems().isEmpty()) {
                throw new BusinessException("Список ингредиентов пуст");
            }
            ObservableList<IngredientService> selectedItems = ingredients
                    .getSelectionModel().getSelectedItems();
            if (selectedItems.isEmpty()) {
                throw new BusinessException("Не выбрано ни одного ингредиента");
            }
            ModalFactory.confirm(
                    stage,
                    "Удалить только из списка рецепта? " +
                            "При выборе 'Нет' ингредиент будет удалён из базы данных.",
                    () -> {
                        selectedItems.forEach(RelationService::removeByIngredient);
                        ingredients.getItems().removeAll(selectedItems);
                    }, () -> {
                        selectedItems.forEach(RelationService::removeByIngredient);
                        selectedItems.forEach(IngredientService::remove);
                        ingredients.getItems().removeAll(selectedItems);
                    }
            );
        }, stage);
    }
}

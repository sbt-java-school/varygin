package lesson24.view.views.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import lesson24.db.shema.Recipe;
import lesson24.services.IngredientService;
import lesson24.services.RecipeService;
import lesson24.services.RelationService;
import lesson24.view.Control;
import lesson24.view.views.home.HomePage;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;
import static javafx.collections.FXCollections.*;

/**
 * Список рецептов и поиск по названию рецепта
 */
public class SearchBlock implements Control {
    private Stage stage;
    private HomePage control;

    @FXML
    private TextField query;
    @FXML
    private Button searchButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button editButton;
    @FXML
    private ListView<Recipe> recipes;
    @FXML
    private ListView<IngredientService> ingredients;
    @FXML
    private Label title;
    @FXML
    private TextArea description;

    private ObservableList ingredientsList;

    public SearchBlock() {
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParent(Control control) {
        this.control = (HomePage) control;
    }

    @FXML
    private void initialize() {
        ingredientsList = observableArrayList(RecipeService.getList());
        recipes.getItems().addAll(ingredientsList);
        setListView();
    }

    private void setListView() {
        recipes.setCellFactory(
                param -> new ListCell<Recipe>() {
                    @Override
                    protected void updateItem(Recipe item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || isNull(item)) {
                            setText(null);
                        } else {
                            setText(item.getName());

                        }
                    }
                }
        );
    }

    @FXML
    private void search() {
        //Filter list by name
    }

    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void edit() {
        stage.close();
    }

    @FXML
    private void detailInfo() {
        title.setText("");
        description.setText("");
        Recipe selectedItem = recipes.getSelectionModel().getSelectedItem();
        if (isNull(selectedItem)) {
            return;
        }

        title.setText(selectedItem.getName());
        description.setText(selectedItem.getDescription());
        List<IngredientService> serviceList = RelationService.getIngredientsByRecipeId(selectedItem.getId());
        ingredients.setItems(observableArrayList(serviceList));
    }
}

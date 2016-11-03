package lesson24.view.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lesson24.view.Control;
import lesson24.view.ModalFactory;

public class HomePage implements Control {
    private Control control;
    @FXML
    private Button searchButton;
    @FXML
    private Button createButton;
    @FXML
    private Button exitButton;

    private Stage stage;

    public HomePage() {
    }

    @Override
    public void setParent(Control control) {
        this.control = null;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void search() {
        ModalFactory.create(getClass().getResource("../search/search.fxml"),
                "Поиск", stage, control -> control.setParent(this));
    }

    @FXML
    private void create() {
        ModalFactory.create(getClass().getResource("../recipe/create.fxml"),
                "Добавление рецепта", stage, control -> control.setParent(this));
    }

    @FXML
    private void exit() {
        stage.close();
    }
}

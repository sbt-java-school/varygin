package lesson24.view.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lesson24.view.Main;
import lesson24.view.views.Control;
import lesson24.view.views.ModalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Контроллер главной страницы приложения
 */
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
        ModalFactory.create("views\\search.fxml", "Поиск",
                stage, control -> control.setParent(this));
    }

    @FXML
    private void create() {
        ModalFactory.create("views\\create.fxml", "Добавление рецепта",
                stage, control -> control.setParent(this));
    }

    @FXML
    private void exit() {
        stage.close();
    }
}

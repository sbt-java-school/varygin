package lesson24.view.views.home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lesson24.exceptions.BusinessException;
import lesson24.view.Control;
import lesson24.view.Main;
import lesson24.view.ModalFactory;

import java.io.IOException;

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

    }

    @FXML
    private void create() {
        try {
            ModalFactory.create(
                    getClass().getResource("../recipe/create.fxml"),
                    "Добавление рецепта",
                    stage,
                    this
            );
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }

    @FXML
    private void exit() {
        stage.close();
    }
}

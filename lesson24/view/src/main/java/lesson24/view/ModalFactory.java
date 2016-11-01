package lesson24.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ModalFactory {
    public static void create(URL resource, String title, Stage owner, Control control) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(resource);
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            Control controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setParent(control);

            dialogStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void error(Stage stage, String message, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(stage);
        alert.setTitle("Ошибка");
        alert.setHeaderText(message);
        alert.setContentText(text);

        alert.showAndWait();
    }
}

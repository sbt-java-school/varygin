package lesson24.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lesson24.exceptions.BusinessException;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.Callable;

import static javafx.scene.control.Alert.*;
import static javafx.scene.control.ButtonBar.*;

public class ModalFactory {
    public static void create(URL resource, String title, Stage owner, Callback callback) {
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
            callback.call(controller);

            dialogStage.showAndWait();
        } catch (IOException e) {
            ModalFactory.error(owner, "Невозможно создать всплывающее окно, " +
                    "повторите попытку или перезагрузите приложение");
        }
    }

    public static void error(Stage stage, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void confirm(Stage stage, String message, Runnable forYes, Runnable forNo) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType yes = new ButtonType("Да", ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Нет", ButtonData.NO);
        ButtonType cancel = new ButtonType("Отмена", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancel, no, yes);

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent()) {
            return;
        }
        ButtonData buttonData = result.get().getButtonData();
        if (buttonData == ButtonData.OK_DONE) {
            forYes.run();
        } else if (buttonData == ButtonData.NO) {
            forNo.run();
        }
    }

    public static void wrap(Runnable action, Stage stage) {
        try {
            action.run();
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }
}

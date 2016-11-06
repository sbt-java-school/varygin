package lesson24.view.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lesson24.exceptions.BusinessException;
import lesson24.view.Main;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.ButtonBar.ButtonData;

/**
 * Класс генирации модальных окон приолжения
 */
public class ModalFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * Создание нового модального окна приложения
     *
     * @param fileName название xml файла представления
     * @param title название окна
     * @param owner вызывающее окно
     * @param callback функция по заполнению содержимого по умолчанию, создаваемого окна
     */
    public static void create(String fileName, String title, Stage owner, Callback callback) {
        try {
            File file = new File(fileName);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            FXMLLoader loader = new FXMLLoader();
            AnchorPane page = loader.load(new ByteArrayInputStream(bytes));

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
            LOGGER.debug(e.getMessage());
        }
    }

    /**
     * Вывод окошка с сообщением об ошибке
     * @param stage текущее окно
     * @param message сообщение
     */
    private static void error(Stage stage, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Метод создания окна с подтверждением выполняемых действий
     *
     * @param stage текущее окно
     * @param message задаваемый вопрос
     * @param forYes действия в случае подтверждения
     * @param forNo действия в случае отрицания
     */
    public static void confirm(Stage stage, String message,
                               Runnable forYes, Runnable forNo) {
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

    /**
     * Обёртка для единого интерфейса отлова сервисных ошибок
     * @param action выполняемая функция
     * @param stage текущее окно
     */
    public static void wrap(Runnable action, Stage stage) {
        try {
            action.run();
        } catch (BusinessException e) {
            ModalFactory.error(stage, e.getMessage());
        }
    }
}

package lesson24.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lesson24.services.MainService;
import lesson24.view.views.home.HomePage;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Класс для запуска программы
 */
public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Рецепты");

        initRootLayOut();
    }

    /**
     * Создание окна-меню приложения
     */
    private void initRootLayOut() {
        try {
            File file = new File("views/home.fxml");
            byte[] bytes = FileUtils.readFileToByteArray(file);

            FXMLLoader loader = new FXMLLoader();
            AnchorPane rootLayout = loader.load(new ByteArrayInputStream(bytes));

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            HomePage controller = loader.getController();
            controller.setStage(primaryStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MainService.createTables();
        launch(args);
    }
}

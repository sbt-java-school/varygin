package window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sbt.lesson18.com.part2.Client;
import sbt.lesson18.com.part2.exceptions.ConnectionException;
import window.view.Controller;

import java.io.IOException;

/**
 * Основа для генерации окна приложения
 */
public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Чат");

        initRootLayOut();
    }

    private void initRootLayOut() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getClassLoader().getResource("window/view/client.fxml"));
        try {
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            Controller controller = loader.getController();
            try {
                Client client = new Client(controller);
                controller.setMainApp(this, client);

                Thread thread = new Thread(client);
                thread.setDaemon(true);
                thread.start();
            } catch (ConnectionException e) {
                //ignore
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

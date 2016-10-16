package window.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sbt.lesson18.com.part2.Client;
import sbt.lesson18.com.part2.dao.Message;
import sbt.lesson18.com.part2.service.Command;
import window.Main;

import static javafx.scene.control.Alert.AlertType;

public class Controller {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private Button closeButton;
    @FXML
    private Button sendButton;
    @FXML
    private Button getButton;
    @FXML
    private Label textLabel;
    @FXML
    private ChoiceBox<String> usersSelector;

    private Main mainApp;
    private Client client;

    public Controller() {
    }

    @FXML
    private void initialize() {
        textLabel.setText("Логин:");
        textArea.setEditable(false);
        getButton.setDisable(true);
        closeButton.setDisable(true);
        sendButton.setDisable(true);
    }

    public void setMainApp(Main mainApp, Client client) {
        this.mainApp = mainApp;
        this.client = client;
    }

    @FXML
    private void handleSendButtonAction() {
        if (sendButton.isDisabled()) {
            return;
        }
        String text = textField.getText();
        if (!text.isEmpty()) {
            textField.clear();

            Message message = client.prepareMessage(text);
            print(message.getMessage());
            client.addMessage(message);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Введите текст");

            alert.showAndWait();
        }
    }

    @FXML
    public void handleCloseButtonAction() {
        client.addMessage(new Message(Command.EXIT));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleGetButtonAction() {
        client.addMessage(new Message(Command.GET_ALL));
    }

    public void addUserToList(String login) {
        ObservableList<String> items = usersSelector.getItems();
        if (!items.contains(login)) {
            items.add(login);
        }
    }

    public void print(String text) {
        textArea.appendText(text + "\n");
    }

    public void setLabel(String label) {
        Platform.runLater(() -> textLabel.setText(label));
    }

    public void activateGet() {
        getButton.setDisable(false);
    }

    public String getRecipient() {
        return usersSelector.getValue();
    }

    public void enableBtn() {
        closeButton.setDisable(false);
        sendButton.setDisable(false);
    }
}

package lesson24.view.views;

import javafx.stage.Stage;

public interface Control {
    void setStage(Stage stage);
    void setParent(Control control);
}

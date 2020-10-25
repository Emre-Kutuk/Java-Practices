package stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Notification {
  public Notification(String message, Stage owner) {
    Stage errorStage = new Stage();
    errorStage.setTitle("Notification | Guitarshop Fx ");
    errorStage.setHeight(150);
    errorStage.setWidth(400);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER_LEFT);
    gridPane.setPadding(new Insets(20));
    gridPane.setVgap(15);

    Label errorLabel = new Label(message);
    GridPane.setConstraints(errorLabel, 0, 0);

    Button closeButton = new Button("OK");
    GridPane.setConstraints(closeButton, 0, 1);
    closeButton.setMinWidth(60);

    gridPane.getChildren().addAll(errorLabel, closeButton);

    Scene errorScene = new Scene(gridPane);
    errorScene.getStylesheets().add("css/style.css");

    errorStage.setScene(errorScene);
    errorStage.setResizable(false);

    errorStage.initModality(Modality.WINDOW_MODAL);
    errorStage.initOwner(owner);
    errorStage.show();
    closeButton.setOnAction(e -> errorStage.close());
  }
}

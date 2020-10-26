package stage;

import exception.AccountLockedException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;
import service.User_SERVICE;

public class Login {

  private final Stage loginStage;
  private int attemptCount = 3;
  private String tempUserName;

  public Login() {
    Stage loginStage = new Stage();
    this.loginStage = loginStage;
    loginStage.setTitle("Guitarshop FX - Login");
    loginStage.setHeight(250);
    loginStage.setWidth(400);

    Scene loginScene = new Scene(SetContent());
    loginScene.getStylesheets().add("css/style.css");
    loginStage.setScene(loginScene);

    loginStage.setResizable(false);
    loginStage.show();
  }

  private GridPane SetContent() {
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(10);
    gridPane.setHgap(8);

    Label username = new Label("Username");
    TextField usernameInput = new TextField();
    usernameInput.setPromptText("username");

    Label password = new Label("Password");
    PasswordField passwordInput = new PasswordField();
    passwordInput.setPromptText("password");

    Button loginButton = new Button();
    loginButton.setText("Login");
    loginButton.setMinWidth(100f);

    GridPane.setConstraints(username, 0, 1);
    GridPane.setConstraints(usernameInput, 1, 1);
    GridPane.setConstraints(password, 0, 2);
    GridPane.setConstraints(passwordInput, 1, 2);
    GridPane.setConstraints(loginButton, 1, 3);

    gridPane.getChildren().addAll(username, usernameInput, password, passwordInput, loginButton);

    loginButton.setOnAction(
        e -> {
          User user = null;
          try
          {
            user = ReturnUserIfExists(usernameInput.getText(), passwordInput.getText());
          } catch (AccountLockedException accountLockedException)
          {
            accountLockedException.printStackTrace();
          }

          if (user == null) {
            try {
              TrackWrongAttempt(usernameInput.getText());
            } catch (AccountLockedException accountLockedException) {

            }
          } else {
            new Dashboard(user);
            loginStage.close();
          }
        });

    return gridPane;
  }

  private User ReturnUserIfExists(String username, String password) throws AccountLockedException
  {
    User_SERVICE userService = new User_SERVICE();

    for (User u : userService.GetAllUsers()) {
      if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
        return u;
      }
    }
    return null;
  }

  private void TrackWrongAttempt(String userName) throws AccountLockedException {

    if (attemptCount > 1) {

      if (tempUserName == null) tempUserName = userName;
      else if (tempUserName.equals(userName)) {
        attemptCount--;
      } else {
        tempUserName = userName;
        attemptCount = 3;
      }

      new Notification(String.format("Wrong username or password, %s attempts left", attemptCount),loginStage);

    } else throw new AccountLockedException();
  }
}

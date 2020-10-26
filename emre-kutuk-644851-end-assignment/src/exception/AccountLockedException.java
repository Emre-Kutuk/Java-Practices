package exception;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AccountLockedException extends Exception {
  public AccountLockedException() {
    Alert alert = new Alert(Alert.AlertType.ERROR, "Account blocked", ButtonType.OK);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) System.exit(0);
  }
}

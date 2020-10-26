package stage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import service.Customer_SERVICE;

public class AddCustomer extends CustomerList {

  private Stage addCustomerStage;
  private Stage owner;
  private CustomerList parent;

  public AddCustomer(Stage owner, CustomerList parent) {
    super();
    this.owner = owner;
    this.parent = parent;

    addCustomerStage = new Stage();
    addCustomerStage.setWidth(400);
    addCustomerStage.setHeight(600);
    addCustomerStage.setTitle("Add Customer | Guitarshop FX");

    VBox labels = new VBox();
    VBox userInput = new VBox();
    HBox buttons = new HBox();

    Label title = new Label("New Customer");
    title.setId("h1");

    Label firstName = new Label("First name");
    Label lastName = new Label("Last name");
    Label address = new Label("Street address");
    Label city = new Label("City");
    Label phoneNumber = new Label("Phone number");
    Label emailAddress = new Label("Email address");

    labels.setSpacing(30);
    labels.setPadding(new Insets(10));
    labels.getChildren().addAll(firstName, lastName, address, city, phoneNumber, emailAddress);

    TextField firstNameInput = new TextField();
    TextField lastNameInput = new TextField();
    TextField addressInput = new TextField();
    TextField cityInput = new TextField();
    TextField phoneNumberInput = new TextField();
    TextField emailAddressInput = new TextField();

    userInput.setSpacing(20);
    userInput.setPadding(new Insets(10));
    userInput
        .getChildren()
        .addAll(
            firstNameInput,
            lastNameInput,
            addressInput,
            cityInput,
            phoneNumberInput,
            emailAddressInput);

    Button addCustomerButton = new Button("Add Customer");
    Button cancelButton = new Button("Cancel");

    buttons.getChildren().addAll(addCustomerButton, cancelButton);
    buttons.setSpacing(20);
    buttons.setPadding(new Insets(10));

    HBox hBox = new HBox();
    hBox.getChildren().addAll(labels, userInput);
    hBox.setSpacing(20);
    VBox vBox = new VBox();
    vBox.getChildren().addAll(title, hBox, buttons);

    Scene addCustomerScene = new Scene(vBox);
    addCustomerScene.getStylesheets().add("css/style.css");

    cancelButton.setOnAction(e -> Cancel());
    addCustomerButton.setOnAction(
        e ->
            AddTheCustomer(
                firstNameInput,
                lastNameInput,
                addressInput,
                cityInput,
                phoneNumberInput,
                emailAddressInput));

    addCustomerStage.initOwner(owner);
    addCustomerStage.initModality(Modality.WINDOW_MODAL);

    addCustomerStage.setScene(addCustomerScene);
    addCustomerStage.setResizable(false);
    addCustomerStage.show();
  }

  private void AddTheCustomer(
      TextField firstName,
      TextField lastName,
      TextField address,
      TextField city,
      TextField phoneNumber,
      TextField emailAddress) {
    Customer_SERVICE customerService = new Customer_SERVICE();

    try {
      customerService.AddOneCustomer(
          new Customer(
              firstName.getText(),
              lastName.getText(),
              city.getText(),
              emailAddress.getText(),
              address.getText(),
              phoneNumber.getText()));
      addCustomerStage.close();
      owner.close();
      new Notification("Customer has been added successfully.", parent.dashboardStage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void Cancel() {
    addCustomerStage.close();
  }
}

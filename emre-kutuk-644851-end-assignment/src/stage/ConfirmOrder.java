package stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Order;
import service.Article_SERVICE;
import service.Customer_SERVICE;

import java.text.NumberFormat;

public class ConfirmOrder extends Dashboard {
  private final Stage confirmOrderStage;
  private final Dashboard dashboard;

  public ConfirmOrder(Dashboard dashboard) {
    this.dashboard = dashboard;

    confirmOrderStage = new Stage();
    confirmOrderStage.setWidth(600);
    confirmOrderStage.setHeight(600);
    confirmOrderStage.setTitle("Confirm Order | Guitarshop FX");
    confirmOrderStage.setResizable(false);

    VBox vBox = new VBox();
    vBox.setPadding(new Insets(20));
    vBox.setSpacing(20);

    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    Label totalPriceLabel =
        new Label(
            String.format(
                "Total Price: %s",
                formatter.format(dashboard.customerOrderPart.getTotalOrderPrice())));
    totalPriceLabel.setId("h2");
    Button confirmButton = new Button("Confirm");

    vBox.getChildren()
        .addAll(SetCustomerDetails(), SetOrdersTable(), totalPriceLabel, confirmButton);
    Scene confirmOrderScene = new Scene(vBox);
    confirmOrderScene.getStylesheets().add("css/style.css");

    confirmButton.setOnAction(e -> Confirm());

    confirmOrderStage.initOwner(dashboard.dashboardStage);
    confirmOrderStage.initModality(Modality.WINDOW_MODAL);
    confirmOrderStage.setScene(confirmOrderScene);
    confirmOrderStage.show();
  }

  private void Confirm() {
    Customer_SERVICE customerService = new Customer_SERVICE();
    Article_SERVICE articleService = new Article_SERVICE();

    for (Order o : dashboard.customerOrderPart.getUnfinishedOrders()) {
      articleService.ChangeArticleQuantity(o.getArticle(), o.getQuantity(), false);
    }

    customerService.AddOneCustomer(dashboard.customerOrderPart);
    confirmOrderStage.close();
    dashboard.ResetOrderView();
    new Notification("Order has been successfully added.", dashboard.dashboardStage);
  }

  private HBox SetCustomerDetails() {
    VBox customerDetails = new VBox();

    Label customer = new Label("Customer:");
    Label customerName =
        new Label(
            String.format(
                "%s %s",
                dashboard.customerOrderPart.getName(), dashboard.customerOrderPart.getLastName()));
    Label customerAddress = new Label(dashboard.customerOrderPart.getAddress());
    Label customerCity = new Label(dashboard.customerOrderPart.getCity());
    Label customerPhone = new Label(dashboard.customerOrderPart.getPhoneNumber());
    Label customerEmail = new Label(dashboard.customerOrderPart.getEmail());

    customerDetails
        .getChildren()
        .addAll(customerName, customerAddress, customerCity, customerPhone, customerEmail);
    customerDetails.setSpacing(5);

    HBox hBoxCustomerInfo = new HBox();
    hBoxCustomerInfo.setSpacing(10);
    hBoxCustomerInfo.getChildren().addAll(customer, customerDetails);

    return hBoxCustomerInfo;
  }

  private TableView SetOrdersTable() {
    TableView ordersTable = new TableView();

    // quantity column
    TableColumn<Order, Integer> quantityColumn = new TableColumn<>("Quantity");
    quantityColumn.setMinWidth(50);
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    // brand column
    TableColumn<Order, String> brandColumn = new TableColumn<>("Brand");
    brandColumn.setMinWidth(100);
    brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

    // model column
    TableColumn<Order, String> modelColumn = new TableColumn<>("Model");
    modelColumn.setMinWidth(100);
    modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

    // type column
    TableColumn<Order, String> typeColumn = new TableColumn<>("Type");
    typeColumn.setMinWidth(100);
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    // price column
    TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(100);
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    priceColumn.setCellFactory(
        tc ->
            new TableCell<Order, Double>() {

              @Override
              protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                  setText(null);
                } else {
                  setText(currencyFormat.format(price));
                }
              }
            });

    if (ordersTable != null) {
      ObservableList<Order> orders =
          FXCollections.observableArrayList(dashboard.customerOrderPart.getUnfinishedOrders());
      ordersTable.setItems(orders);
    }

    ordersTable
        .getColumns()
        .addAll(quantityColumn, brandColumn, modelColumn, typeColumn, priceColumn);

    return ordersTable;
  }
}

package stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import service.Customer_SERVICE;

import java.util.ArrayList;
import java.util.List;

public class CustomerList extends Dashboard {
  private final Stage customerListStage;
  private final TextField searchInput;
  private final Dashboard dashboard;
  private List<Customer> customersResult = new ArrayList<>();
  Customer_SERVICE customerService = new Customer_SERVICE();

  public CustomerList(TextField searchInput, Dashboard dashboard) {
    super();
    this.searchInput = searchInput;
    this.dashboard = dashboard;
    ConductSearch();
    VBox vBox = new VBox();
    Label title = new Label("Customers List");
    title.setId("h1");

    vBox.setPadding(new Insets(10));
    vBox.setSpacing(10);
    vBox.getChildren().addAll(title, SetCustomersTable());
    Scene customerListScene = new Scene(vBox);
    customerListScene.getStylesheets().add("css/style.css");

    customerListStage = new Stage();
    customerListStage.setResizable(false);
    customerListStage.setScene(customerListScene);
    customerListStage.setTitle("Search Customer | Guitarshop FX");
    customerListStage.setWidth(600);
    customerListStage.setHeight(400);
    customerListStage.initOwner(dashboard.dashboardStage);
    customerListStage.initModality(Modality.WINDOW_MODAL);

    customerListStage.show();
  }

  private TableView SetCustomersTable() {
    TableView customersTable = new TableView();

    // first name column
    TableColumn<Customer, String> nameColumn = new TableColumn<>("First Name");
    nameColumn.setMinWidth(50);
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    // last name column
    TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Last Name");
    lastNameColumn.setMinWidth(50);
    lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    // address column
    TableColumn<Customer, String> addressColumn = new TableColumn<>("Street Address");
    addressColumn.setMinWidth(50);
    addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

    // city column
    TableColumn<Customer, String> cityColumn = new TableColumn<>("City");
    cityColumn.setMinWidth(50);
    cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

    // phone column
    TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
    phoneColumn.setMinWidth(50);
    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    // email column
    TableColumn<Customer, String> mailColumn = new TableColumn<>("Email");
    mailColumn.setMinWidth(50);
    mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    customersTable.setRowFactory(
        tv -> {
          TableRow<Customer> row = new TableRow<>();
          row.setOnMouseClicked(
              event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                  dashboard.ResetOrderView();
                  dashboard.customerOrderPart = row.getItem();
                  dashboard.filteredOrdersByCustomer = row.getItem().getOrders();
                  dashboard.DisplayOrderView();
                  customerListStage.close();
                }
              });
          return row;
        });

    ObservableList<Customer> customerObservableList =
        FXCollections.observableArrayList(customersResult);
    customersTable.setItems(customerObservableList);

    customersTable
        .getColumns()
        .addAll(nameColumn, lastNameColumn, addressColumn, cityColumn, phoneColumn, mailColumn);

    return customersTable;
  }

  private void ConductSearch() {
    if (!searchInput.getText().equals("")) {
      String keyword = searchInput.getText().toLowerCase();

      for (Customer c : customerService.GetAllCustomers()) {
        if (c.getName().toLowerCase().contains(keyword)
            || c.getLastName().toLowerCase().contains(keyword)) customersResult.add(c);
      }
    } else customersResult = customerService.GetAllCustomers();
  }
}

package stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import model.*;
import nl.inholland.exam.WelcomeMessage_DAO;
import service.Article_SERVICE;
import service.Customer_SERVICE;
import service.User_SERVICE;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Dashboard {

  // Dashboard Part
  protected Stage dashboardStage;
  private Scene dashboardScene;
  private VBox dashboardView;
  private User user;

  // Maintenance Part
  private TableView<Article> articleTableView;

  // Order Part
  private TableView<Order> orderTableView;
  protected Customer customerOrderPart;
  protected List<Order> filteredOrdersByCustomer;
  protected List<Article> allArticles;

  // Order List Part
  private Customer customerOrderListPart;

  // Services
  User_SERVICE userService;
  Article_SERVICE articleService;
  Customer_SERVICE customerService;
  WelcomeMessage_DAO message_db;

  public Dashboard(User user) {
    Stage dashboardStage = new Stage();
    VBox dashboardView = new VBox();
    Scene dashboardScene = new Scene(dashboardView);

    articleService = new Article_SERVICE();
    userService = new User_SERVICE();
    customerService = new Customer_SERVICE();
    message_db = new WelcomeMessage_DAO();

    allArticles = articleService.GetAllArticles();

    this.dashboardStage = dashboardStage;
    dashboardStage.setResizable(false);
    this.dashboardScene = dashboardScene;
    this.dashboardView = dashboardView;
    this.user = user;

    dashboardScene.getStylesheets().add("css/style.css");
    DisplayHomeView();
  }

  public Dashboard() {}

  private void DisplayOrderListView() {
    dashboardStage.setTitle(
        String.format(
            "%s %s - View Order List | Guitarshop FX", user.getName(), user.getLastName()));

    dashboardView.getChildren().clear();
    VBox vBox = new VBox();
    vBox.setSpacing(20);
    vBox.setPadding(new Insets(20));

    Label orderListLabel = new Label("Order List");
    orderListLabel.setId("h1");

    vBox.getChildren()
        .addAll(
            orderListLabel,
            SetCustomerWithOrderTable(),
            SetOrdersTableByCustomer(customerOrderListPart));

    dashboardView.getChildren().addAll(SetMenuBar(), vBox);
  }

  private TableView SetCustomerWithOrderTable() {
    TableView customersWithOrderTable = new TableView();
    List<Customer> customersWithOrder = new ArrayList<>();
    for (Customer c : customerService.GetAllCustomers())
      if (c.getOrders().size() > 0) customersWithOrder.add(c);

    // date column
    TableColumn<Customer, String> dateColumn = new TableColumn<>("Date");
    dateColumn.setMinWidth(100);
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInString"));

    // full name column
    TableColumn<Customer, String> fullNameColumn = new TableColumn<>("Customer Name");
    fullNameColumn.setMinWidth(130);
    fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

    // city column
    TableColumn<Customer, String> cityColumn = new TableColumn<>("City");
    cityColumn.setMinWidth(100);
    cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

    // phone column
    TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
    phoneColumn.setMinWidth(100);
    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    // mail column
    TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
    emailColumn.setMinWidth(100);
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

    // count column
    TableColumn<Customer, Integer> countColumn = new TableColumn<>("Count");
    countColumn.setMinWidth(50);
    countColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfOrders"));

    // price column
    TableColumn<Customer, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(100);
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalFinishedOrderPrice"));

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    priceColumn.setCellFactory(
        tc ->
            new TableCell<Customer, Double>() {

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

    if (customersWithOrder != null) {
      ObservableList<Customer> customers = FXCollections.observableArrayList(customersWithOrder);
      customersWithOrderTable.setItems(customers);
    }

    customersWithOrderTable
        .getColumns()
        .addAll(
            dateColumn,
            fullNameColumn,
            cityColumn,
            phoneColumn,
            emailColumn,
            countColumn,
            priceColumn);

    customersWithOrderTable.setRowFactory(
        tv -> {
          TableRow<Customer> row = new TableRow<>();
          row.setOnMouseClicked(
              event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                  Customer selectedCustomer = row.getItem();
                  customerOrderListPart = selectedCustomer;
                  DisplayOrderListView();
                }
              });
          return row;
        });

    return customersWithOrderTable;
  }

  private TableView SetOrdersTableByCustomer(Customer customer) {
    TableView ordersByCustomerTable = new TableView();

    // brand column
    TableColumn<Order, String> brandColumn = new TableColumn<>("Brand");
    brandColumn.setMinWidth(50);
    brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

    // model column
    TableColumn<Order, String> modelColumn = new TableColumn<>("Model");
    modelColumn.setMinWidth(50);
    modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

    // acoustic column
    TableColumn<Order, Boolean> acousticColumn = new TableColumn<>("Acoustic");
    acousticColumn.setMinWidth(50);
    acousticColumn.setCellValueFactory(new PropertyValueFactory<>("acoustic"));

    // type column
    TableColumn<Order, String> typeColumn = new TableColumn<>("Type");
    typeColumn.setMinWidth(50);
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    // price column
    TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(50);
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("articlePrice"));

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

    // quantity column
    TableColumn<Order, Integer> quantityColumn = new TableColumn<>("Quantity");
    quantityColumn.setMinWidth(50);
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    if (customer != null) {
      ObservableList<Order> orders = FXCollections.observableArrayList(customer.getOrders());
      ordersByCustomerTable.setItems(orders);
    }

    ordersByCustomerTable
        .getColumns()
        .addAll(brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn, quantityColumn);

    return ordersByCustomerTable;
  }

  protected void DisplayOrderView() {
    if (this.user.getType() == UserType.salesPerson) {
      dashboardStage.setTitle(
          String.format("%s %s - Order | Guitarshop FX", user.getName(), user.getLastName()));

      dashboardView.getChildren().clear();
      VBox vBox = new VBox();
      HBox hBox = new HBox();
      HBox hBox2 = new HBox();
      vBox.setPadding(new Insets(15));
      hBox.setPadding(new Insets(0, 15, 0, 15));
      hBox.setSpacing(10);

      hBox2.setSpacing(50);

      Label title = new Label("Create Order");
      title.setId("h1");

      Label customerLabel = new Label("Customer");
      customerLabel.setId("h2");

      TextField searchInput = new TextField();
      searchInput.setPromptText("search for customer");

      Button searchButton = new Button("Search");

      Label articlesLabel = new Label("Articles");
      articlesLabel.setId("h2");

      SetOrderTable();
      SetOrderControllers();

      searchButton.setOnAction(e -> new CustomerList(searchInput, this));

      hBox.getChildren().addAll(searchInput, searchButton);
      hBox2.getChildren().addAll(hBox, SetCustomerInfoBox());
      vBox.getChildren()
          .addAll(
              title, customerLabel, hBox2, articlesLabel, orderTableView, SetOrderControllers());
      dashboardView.getChildren().addAll(SetMenuBar(), vBox);
    } else new Notification("You don't have permission to access this menu.", dashboardStage);
  }

  private Pane SetCustomerInfoBox() {
    Pane customerInfoBox = new Pane();
    customerInfoBox.setId("panel");
    customerInfoBox.setMinWidth(600);
    customerInfoBox.setMinHeight(100);

    VBox vBox = new VBox();
    vBox.setId("panel-context");
    Label firstName = new Label("First name:");
    Label address = new Label("Street address:");
    Label number = new Label("Phone number:");

    VBox vBox1 = new VBox();
    vBox1.setId("panel-context");
    Label firstNameC = new Label("");
    Label addressC = new Label("");
    Label numberC = new Label("");

    VBox vBox2 = new VBox();
    vBox2.setId("panel-context");
    Label lastName = new Label("Last name:");
    Label city = new Label("City");
    Label email = new Label("Email address:");

    VBox vBox3 = new VBox();
    vBox3.setId("panel-context");
    Label lastNameC = new Label("");
    Label cityC = new Label("");
    Label emailC = new Label("");

    if (this.customerOrderPart != null) {

      firstNameC.setText(customerOrderPart.getName());
      addressC.setText(customerOrderPart.getAddress());
      numberC.setText(customerOrderPart.getPhoneNumber());
      lastNameC.setText(customerOrderPart.getLastName());
      cityC.setText(customerOrderPart.getCity());
      emailC.setText(customerOrderPart.getEmail());
    }

    vBox.getChildren().addAll(firstName, address, number);
    vBox1.getChildren().addAll(firstNameC, addressC, numberC);
    vBox2.getChildren().addAll(lastName, city, email);
    vBox3.getChildren().addAll(lastNameC, cityC, emailC);

    HBox hBox = new HBox();
    hBox.setSpacing(50);
    hBox.setPadding(new Insets(10));
    hBox.getChildren().addAll(vBox, vBox1, vBox2, vBox3);
    customerInfoBox.getChildren().addAll(hBox);
    return customerInfoBox;
  }

  private HBox SetOrderControllers() {
    HBox orderControllers = new HBox();

    Button addButton = new Button("Add");
    Button deleteButton = new Button("Delete");
    Button confirmButton = new Button("Confirm");
    Button resetButton = new Button("Reset");

    orderControllers.getChildren().addAll(addButton, deleteButton, confirmButton, resetButton);
    orderControllers.setSpacing(5);
    orderControllers.alignmentProperty().set(Pos.CENTER);
    orderControllers.setPadding(new Insets(10));

    addButton.setOnAction(e -> new AddArticle(customerOrderPart, this, allArticles));
    resetButton.setOnAction(e -> ResetOrderView());
    deleteButton.setOnAction(e -> RemoveOrderFromOrderView());
    confirmButton.setOnAction(e -> new ConfirmOrder(this));

    return orderControllers;
  }

  private void RemoveOrderFromOrderView() {
    if (orderTableView.getSelectionModel().getSelectedItem() != null) {

      Order toBeRemovedOrder = orderTableView.getSelectionModel().getSelectedItem();
      for (Article a : allArticles) {
        if (a.equals(toBeRemovedOrder.getArticle())) {
          a.setQuantity(toBeRemovedOrder.getQuantity(), true);
          customerOrderPart.deleteOrder(toBeRemovedOrder);
        }
      }
      filteredOrdersByCustomer.remove(toBeRemovedOrder);
      DisplayOrderView();
    } else new Notification("Please select an order to delete.", dashboardStage);
  }

  protected void ResetOrderView() {
    if (customerOrderPart != null)
      customerOrderPart.setOrders(customerOrderPart.getFinishedOrders());
    allArticles = articleService.GetAllArticles();
    DisplayOrderView();
  }

  private void SetOrderTable() {
    orderTableView = new TableView();

    // quantity column
    TableColumn<Order, Integer> quantityColumn = new TableColumn<>("Quantity");
    quantityColumn.setMinWidth(50);
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    // brand column
    TableColumn<Order, String> brandColumn = new TableColumn<>("Brand");
    brandColumn.setMinWidth(50);
    brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

    // model column
    TableColumn<Order, String> modelColumn = new TableColumn<>("Model");
    modelColumn.setMinWidth(50);
    modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

    // acoustic column
    TableColumn<Order, Boolean> acousticColumn = new TableColumn<>("Acoustic");
    acousticColumn.setMinWidth(50);
    acousticColumn.setCellValueFactory(new PropertyValueFactory<>("acoustic"));

    // type column
    TableColumn<Order, String> typeColumn = new TableColumn<>("Type");
    typeColumn.setMinWidth(50);
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    // price column
    TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(50);
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

    if (customerOrderPart != null && customerOrderPart.getOrders() != null) {
      filteredOrdersByCustomer = customerOrderPart.getUnfinishedOrders();
      ObservableList<Order> orders = FXCollections.observableArrayList(filteredOrdersByCustomer);
      orderTableView.setItems(orders);
    }

    orderTableView
        .getColumns()
        .addAll(quantityColumn, brandColumn, modelColumn, acousticColumn, typeColumn, priceColumn);
  }

  private void DisplayMaintainView() {
    if (this.user.getType() == UserType.manager) {
      dashboardStage.setTitle(
          String.format(
              "%s %s - Stock Maintenance | Guitarshop FX", user.getName(), user.getLastName()));

      dashboardView.getChildren().clear();
      VBox vBox = new VBox();
      vBox.setPadding(new Insets(15));

      Label title = new Label("Stock Maintenance");
      title.setId("h1");

      SetArticleTable(articleService.GetAllArticles());
      vBox.getChildren().addAll(title, articleTableView, SetMaintenanceControllers());
      dashboardView.getChildren().addAll(SetMenuBar(), vBox);

    } else new Notification("You don't have permission to access this menu.", dashboardStage);
  }

  private HBox SetMaintenanceControllers() {
    HBox stockControllerContainer = new HBox();
    stockControllerContainer.setSpacing(20);
    stockControllerContainer.setPadding(new Insets(20));
    TextField quantityField = new TextField();
    quantityField.setPromptText("type quantity for selected item");
    CheckBox negateCheckBox = new CheckBox("Negate");
    Button changeButton = new Button("Change Stock");
    stockControllerContainer.getChildren().addAll(quantityField, negateCheckBox, changeButton);

    changeButton.setOnAction(
        e -> {
          try {
            Integer.parseInt(quantityField.getText());

            int quantity = Integer.parseInt(quantityField.getText());
            Article article = articleTableView.getSelectionModel().getSelectedItem();

            if (quantity < 0) {
              new Notification("Please enter a positive number.", dashboardStage);
              return;
            }

            if (negateCheckBox.isSelected() && article.getQuantity() <= quantity) {
              new Notification("Stock can not be negative!", dashboardStage);
            } else {
              articleService.ChangeArticleQuantity(article, quantity, !negateCheckBox.isSelected());
              new Notification("Stock has been successfully changed!", dashboardStage);
              DisplayMaintainView();
            }
          } catch (NumberFormatException exception) {
            new Notification("Please type a number", dashboardStage);
            exception.printStackTrace();
          }
        });

    return stockControllerContainer;
  }

  protected TableView SetArticleTable(List<Article> allArticles) {
    articleTableView = new TableView<>();

    // quantity column
    TableColumn<Article, Integer> quantityColumn = new TableColumn<>("Quantity");
    quantityColumn.setMinWidth(50);
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    // brand column
    TableColumn<Article, String> brandColumn = new TableColumn<>("Brand");
    brandColumn.setMinWidth(200);
    brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

    // model column
    TableColumn<Article, String> modelColumn = new TableColumn<>("Model");
    modelColumn.setMinWidth(200);
    modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

    // acoustic column
    TableColumn<Article, Boolean> acousticColumn = new TableColumn<>("Acoustic");
    acousticColumn.setMinWidth(100);
    acousticColumn.setCellValueFactory(new PropertyValueFactory<>("acoustic"));

    // guitar type column
    TableColumn<Article, String> guitarTypeColumn = new TableColumn<>("Guitar Type");
    guitarTypeColumn.setMinWidth(100);
    guitarTypeColumn.setCellValueFactory(new PropertyValueFactory<>("guitarType"));

    if (allArticles != null) {
      ObservableList<Article> articles = FXCollections.observableArrayList(allArticles);
      articleTableView.setItems(articles);
    }

    articleTableView
        .getColumns()
        .addAll(quantityColumn, brandColumn, modelColumn, acousticColumn, guitarTypeColumn);

    return articleTableView;
  }

  private void DisplayHomeView() {

    if (customerOrderPart != null) customerOrderPart = null;

    dashboardStage.setTitle(
        String.format("%s %s - Dashboard | Guitarshop FX", user.getName(), user.getLastName()));
    dashboardStage.setHeight(850);
    dashboardStage.setWidth(1000);

    Label greetingsMsg =
        new Label(String.format("Welcome %s %s", user.getName(), user.getLastName()));
    Label roleMsg = new Label(String.format("Your role is %s", user.getTypeInString()));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    Label dateMsg = new Label(String.format("Today is %s", LocalDateTime.now().format(formatter)));

    greetingsMsg.setId("h1");
    roleMsg.setId("h2");
    dateMsg.setId("h2");

    dashboardView.getChildren().clear();
    dashboardView.getChildren().addAll(SetMenuBar(), greetingsMsg, roleMsg, dateMsg);

    dashboardStage.setScene(dashboardScene);
    dashboardStage.show();
  }

  private MenuBar SetMenuBar() {
    MenuBar menuBar = new MenuBar();

    Menu menuHome = new Menu("Home");
    MenuItem homeButton = new MenuItem("Home");
    menuHome.getItems().addAll(homeButton);

    Menu menuSales = new Menu("Sales");
    MenuItem orderButton = new MenuItem("Order");
    MenuItem listOrdersButton = new MenuItem("List Orders");
    menuSales.getItems().addAll(orderButton, listOrdersButton);

    Menu menuStock = new Menu("Stock");
    MenuItem maintainButton = new MenuItem("Maintain");
    menuStock.getItems().addAll(maintainButton);

    Menu menuLogout = new Menu("Logout");
    MenuItem logoutButton = new MenuItem("Logout");
    MenuItem exitButton = new MenuItem("Exit Application");
    menuLogout.getItems().addAll(logoutButton, exitButton);

    menuBar.getMenus().addAll(menuHome, menuSales, menuStock, menuLogout);

    listOrdersButton.setOnAction(e -> DisplayOrderListView());
    orderButton.setOnAction(e -> DisplayOrderView());
    homeButton.setOnAction(e -> DisplayHomeView());
    logoutButton.setOnAction(e -> Logout());
    exitButton.setOnAction(e -> ExitApp());
    maintainButton.setOnAction(e -> DisplayMaintainView());

    return menuBar;
  }

  private void Logout() {
    new Login();
    dashboardStage.close();
  }

  private void ExitApp() {
    System.exit(0);
  }
}

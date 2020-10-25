package stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Article;
import model.Customer;
import model.Order;

import java.util.List;

public class AddArticle extends Dashboard {

  private final Stage addArticleStage;
  private TableView allArticlesTable;
  private Customer customer;
  private Dashboard dashboard;
  private final List<Article> allArticles;

  public AddArticle(Customer customer, Dashboard dashboard, List<Article> allArticles) {
    addArticleStage = new Stage();
    addArticleStage.setWidth(600);
    addArticleStage.setHeight(400);
    addArticleStage.setTitle("Add Article | Guitarshop FX");

    addArticleStage.setResizable(false);

    this.allArticles = allArticles;

    if (customer == null)
      new Notification("Please select a customer to add an order.", dashboard.dashboardStage);
    else {
      this.customer = customer;
      this.dashboard = dashboard;

      allArticlesTable = super.SetArticleTable(allArticles);

      VBox vBox = new VBox();
      vBox.setPadding(new Insets(15));
      vBox.setSpacing(20);

      vBox.getChildren().addAll(allArticlesTable, SetAddArticleControllers());
      Scene addArticleScene = new Scene(vBox);
      addArticleScene.getStylesheets().add("css/style.css");

      addArticleStage.initOwner(dashboard.dashboardStage);
      addArticleStage.initModality(Modality.WINDOW_MODAL);
      addArticleStage.setScene(addArticleScene);
      addArticleStage.show();
    }
  }

  private HBox SetAddArticleControllers() {
    HBox hBox = new HBox();
    hBox.setSpacing(10);
    hBox.setAlignment(Pos.CENTER);

    TextField articleQuantityTextField = new TextField();
    articleQuantityTextField.setPromptText("Enter quantity for selected article");

    Button addButton = new Button("Add");
    Button cancelButton = new Button("Cancel");

    hBox.getChildren().addAll(articleQuantityTextField, addButton, cancelButton);

    addButton.setOnAction(e -> AddOrderToCustomer(articleQuantityTextField));
    cancelButton.setOnAction(e -> addArticleStage.close());

    return hBox;
  }

  private void AddOrderToCustomer(TextField userInput) {
    try {
      int userQuantity = Integer.parseInt(userInput.getText());
      Article article;
      if (userQuantity < 0) {
        new Notification("Quantity can not be a negative number.", addArticleStage);
        return;
      }
      if (allArticlesTable.getSelectionModel().getSelectedItem() != null) {
        article = (Article) allArticlesTable.getSelectionModel().getSelectedItem();
        for (Article a : allArticles) {
          if (a.equals(article)) {
            if (userQuantity <= a.getQuantity()) {
              customer.addOrder(new Order(userQuantity, article));
              addArticleStage.close();
              a.setQuantity(userQuantity, false);
              dashboard.DisplayOrderView();
            } else {
              new Notification("Stock is not sufficient.", dashboard.dashboardStage);
            }
          }
        }
      } else {
        new Notification("Please select an article.", dashboard.dashboardStage);
      }
    } catch (NumberFormatException e) {
      new Notification("Please type a number", addArticleStage);
    }
  }
}

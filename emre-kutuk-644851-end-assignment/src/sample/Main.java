package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import service.Article_SERVICE;
import service.Customer_SERVICE;
import service.User_SERVICE;
import stage.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

  @Override
  public void start(Stage stage) {
    CreateData();
    new Login();
  }

  public static void main(String[] args) {
    launch(args);
  }

  // Removes everything creates new data
  private void CreateData() {
    User_SERVICE userService = new User_SERVICE();
    Article_SERVICE articleService = new Article_SERVICE();
    Customer_SERVICE customerService = new Customer_SERVICE();

    List<User> users = new ArrayList<>();
    users.add(new User("David", "Gilmour", "manager", "manager", UserType.manager));
    users.add(new User("Roger", "Waters", "sales", "sales", UserType.salesPerson));

    List<Article> articles = new ArrayList<>();
    articles.add(new Article("Fender", "Telecaster", false, "REGULAR", 3, 1079.79));
    articles.add(new Article("Fender", "Precision", false, "BASS", 10, 1300.49));
    articles.add(new Article("Simon Patric", "Pro Flame Maple", true, "REGULAR", 8, 1290.7));

    List<Customer> customers = new ArrayList<>();
    customers.add(
        new Customer(
            "Wim", "Wiltenburg", "Amsterdam", "wim@email.com", "Stentorstraat 90", "06-123456789"));
    customers.add(
        new Customer(
            "Jack", "Traven", "Arnhem", "jack@email.com", "Dorpstraat 10", "06-123654789"));
    customers.add(
        new Customer(
            "Jenny", "Gump", "Den Haag", "jenny@email.com", "Churhstraat 55K", "06-987654321"));

    articleService.AddArticles(articles);
    userService.AddUsers(users);
    customerService.AddCustomers(customers);

    // For Developer
    System.out.printf(
        "In the database;\nThere are %s user(s).\nThere are %s article(s).\nThere are %s customer(s).",
        userService.GetAllUsers().size(),
        articleService.GetAllArticles().size(),
        customerService.GetAllCustomers().size());
  }
}

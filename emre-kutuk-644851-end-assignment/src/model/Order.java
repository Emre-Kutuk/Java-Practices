package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order implements Serializable {
  private final int quantity;
  private final Article article;
  private final LocalDateTime date;
  private final double totalPrice;
  private boolean isComplete;

  public Order(int quantity, Article article) {
    this.quantity = quantity;
    this.article = article;
    date = LocalDateTime.now();
    this.totalPrice = this.article.getPrice() * this.quantity;
    isComplete = false;
  }

  public int getQuantity() {
    return quantity;
  }

  public Article getArticle() {
    return article;
  }

  public String getBrand() {
    return article.getBrand();
  }

  public String getModel() {
    return article.getModel();
  }

  public Boolean getAcoustic() {
    return article.getAcoustic();
  }

  public String getType() {
    return article.getGuitarType();
  }

  public String getDateInString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return formatter.format(date);
  }

  public LocalDateTime getDate() {
    return date;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public double getArticlePrice() {
    return article.getPrice();
  }

  public void setIsComplete(boolean state) {
    isComplete = state;
  }

  public boolean getIsComplete() {
    return isComplete;
  }
}

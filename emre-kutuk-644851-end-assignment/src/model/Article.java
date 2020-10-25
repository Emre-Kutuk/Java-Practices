package model;

import java.io.Serializable;

public class Article implements Serializable {
  private final String brand;
  private final String model;
  private final boolean acoustic;

  private final String guitarType;

  private int quantity;
  private final double price;

  public Article(
      String brand, String model, Boolean acoustic, String guitarType, int quantity, double price) {
    this.brand = brand;
    this.model = model;
    this.acoustic = acoustic;
    this.guitarType = guitarType;
    this.quantity = quantity;
    this.price = price;
  }

  public String getBrand() {
    return brand;
  }

  public String getModel() {
    return model;
  }

  public Boolean getAcoustic() {
    return acoustic;
  }

  public String getGuitarType() {
    return guitarType;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setQuantity(int quantity, Boolean add) {
    if (add) {
      this.quantity += quantity;
    } else this.quantity -= quantity;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Article that = (Article) obj;
    return brand.equals(that.getBrand()) && model.equals(that.getModel());
  }
}

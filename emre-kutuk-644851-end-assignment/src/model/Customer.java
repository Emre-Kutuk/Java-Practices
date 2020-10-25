package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
  private final int customerId;
  private final String name;
  private final String lastName;
  private final String city;
  private final String email;
  private final String address;
  private final String phoneNumber;

  private List<Order> orders;

  private static int id = 0;

  public Customer(
      String name, String lastName, String city, String email, String address, String phoneNumber) {
    this.customerId = id;
    this.name = name;
    this.lastName = lastName;
    this.city = city;
    this.email = email;
    this.address = address;
    this.phoneNumber = phoneNumber;

    orders = new ArrayList<>();

    id++;
  }

  public int getCustomerId() {
    return customerId;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return String.format("%s %s", name, lastName);
  }

  public String getCity() {
    return city;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void addOrder(Order order) {
    orders.add(order);
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public List<Order> getUnfinishedOrders() {
    List<Order> unfinishedOrders = new ArrayList<>();

    for (Order o : orders) if (!o.getIsComplete()) unfinishedOrders.add(o);

    return unfinishedOrders;
  }

  public List<Order> getFinishedOrders() {
    List<Order> finishedOrders = new ArrayList<>();

    for (Order o : orders) if (o.getIsComplete()) finishedOrders.add(o);

    return finishedOrders;
  }

  public void deleteOrder(Order order) {
    orders.removeIf(o -> o.equals(order));
  }

  public double getTotalFinishedOrderPrice() {
    double totalOrderPrice = 0;
    for (Order o : getFinishedOrders()) {
      totalOrderPrice += o.getTotalPrice();
    }
    return totalOrderPrice;
  }

  public double getTotalOrderPrice() {
    double totalOrderPrice = 0;
    for (Order o : getUnfinishedOrders()) {
      totalOrderPrice += o.getTotalPrice();
    }
    return totalOrderPrice;
  }

  public int getNrOfOrders() {
    return orders.size();
  }

  public String getDateInString() {
    return orders.get(0).getDateInString();
  }
}

package dal;

import model.Customer;
import model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Customer_DAO {
  public List<Customer> GetAllCustomers() {
    List<Customer> allCustomers = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream("src/data/customers.dat");
        ObjectInputStream ois = new ObjectInputStream(fis)) {

      while (true) {
        try {
          Customer customer = (Customer) ois.readObject();
          allCustomers.add(customer);
        } catch (EOFException eofe) {
          break;
        }
      }
    } catch (IOException | ClassNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
    return allCustomers;
  }

  public void AddCustomers(List<Customer> customers) {
    {
      try (FileOutputStream fos = new FileOutputStream(new File("src/data/customers.dat"));
          ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        for (Customer c : customers) oos.writeObject(c);

      } catch (IOException fnfe) {
        fnfe.printStackTrace();
      }
    }
  }

  public void AddOrderToCustomer(Order order, Customer customer) {
    List<Customer> customers = GetAllCustomers();
    for (Customer c : customers) {
      if (c.equals(customer)) {
        try {
          c.addOrder(order);
          AddCustomers(customers);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public List<Order> GetOrdersByCustomer(Customer customer) {
    for (Customer c : GetAllCustomers()) {
      if (c.equals(customer)) {
        try {
          return c.getOrders();
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }
    }
    return null;
  }

  public void AddOneCustomer(Customer customer) {
    List<Customer> customers = GetAllCustomers();
    for (Customer c : customers) {
      if (c.getCustomerId() == customer.getCustomerId()) {
        customers.remove(c);

        for (Order o : customer.getOrders()) o.setIsComplete(true);

        customers.add(customer);
        AddCustomers(customers);

        break;
      } else {
        List<Customer> allCustomers = GetAllCustomers();
        allCustomers.add(customer);
        AddCustomers(allCustomers);
        break;
      }
    }
  }
}

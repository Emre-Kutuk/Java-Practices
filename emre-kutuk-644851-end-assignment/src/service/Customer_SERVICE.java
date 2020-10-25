package service;

import dal.Customer_DAO;
import model.Customer;

import java.util.List;

public class Customer_SERVICE {
  Customer_DAO customer_db = new Customer_DAO();

  public List<Customer> GetAllCustomers() {
    try {
      return customer_db.GetAllCustomers();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void AddCustomers(List<Customer> customers) {
    try {
      customer_db.AddCustomers(customers);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void AddOneCustomer(Customer customer)
  {
    try{
      customer_db.AddOneCustomer(customer);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }
}

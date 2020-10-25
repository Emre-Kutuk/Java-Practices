package service;

import dal.User_DAO;
import model.User;

import java.util.List;

public class User_SERVICE {
  User_DAO user_db = new User_DAO();

  public void AddUsers(List<User> users) {
    try {
      user_db.AddUsers(users);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<User> GetAllUsers() {
    try {
      return user_db.GetAllUsers();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

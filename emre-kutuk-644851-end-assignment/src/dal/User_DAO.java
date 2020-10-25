package dal;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User_DAO {

  public List<User> GetAllUsers() {
    List<User> allUsers = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream("src/data/users.dat");
        ObjectInputStream ois = new ObjectInputStream(fis)) {

      while (true) {
        try {
          User user = (User) ois.readObject();
          allUsers.add(user);
        } catch (EOFException eofe) {
          break;
        }
      }
    } catch (IOException | ClassNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
    return allUsers;
  }

  public void AddUsers(List<User> users) {
    {
      try (FileOutputStream fos = new FileOutputStream(new File("src/data/users.dat"));
          ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        for (User u : users) oos.writeObject(u);

      } catch (IOException fnfe) {
        fnfe.printStackTrace();
      }
    }
  }
}

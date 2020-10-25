package model;

import java.io.Serializable;

public class User implements Serializable {
  private final String name;
  private final String lastName;
  private final String username;
  private final String password;
  private final UserType type;

  public User(String name, String lastName, String username, String password, UserType type) {
    this.name = name;
    this.lastName = lastName;

    this.username = username;
    this.password = password;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public UserType getType() {
    return type;
  }

  public String getTypeInString() {
    if (this.getType() == UserType.manager) return "Manager";
    else return "Sales Person";
  }
}

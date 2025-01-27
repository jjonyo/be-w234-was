package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String userId;
  private String password;
  private String name;
  private String email;

  protected User() {}

  private User(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public static User of(String userId, String password, String name, String email) {
    return new User(userId, password, name, email);
  }

  public boolean isCorrectPassword(String comparedPassword) {
    return password.equals(comparedPassword);
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
  }
}

package controller.dto;

import http.HttpParams;

public class CreateUserDto {
  private final String userId;
  private final String password;
  private final String name;
  private final String email;

  private CreateUserDto(String userId, String password, String name, String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public static CreateUserDto of(HttpParams params) {
    return new CreateUserDto(params.getParam("userId"), params.getParam("password"), params.getParam("name"), params.getParam("email"));
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
}

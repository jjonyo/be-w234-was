package controller;

import annotation.PostMapping;
import controller.dto.CreateUserDto;
import http.HttpParams;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import service.UserService;
import utils.HttpRequestUtils;

public class SignUpController implements Controller {

  private static final String MAPPING_URL = "/user/create";
  private final UserService userService = UserService.getInstance();

  @PostMapping
  public void createUser(HttpRequest request, HttpResponse response) {
    HttpParams bodyParams = request.getBody();

    CreateUserDto createUserDto = CreateUserDto.of(bodyParams);

    try {
      userService.createUser(createUserDto);
    } catch (Exception e) {
      response
              .setStatus(HttpStatus.BAD_REQUEST)
              .setBody("Signup fail.".getBytes());
      return;
    }

    response
            .setStatus(HttpStatus.FOUND)
            .addHeader("Location", "/index.html");
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

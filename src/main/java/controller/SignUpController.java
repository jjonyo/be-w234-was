package controller;

import annotation.PostMapping;
import controller.dto.CreateUserDto;
import http.HttpParams;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import repository.InMemoryUserRepository;
import service.UserService;
import utils.HttpRequestUtils;

public class SignUpController implements Controller {

  private static final String MAPPING_URL = "/user/create";
  private final UserService userService = new UserService(new InMemoryUserRepository());

  @PostMapping
  public void createUser(HttpRequest request, HttpResponse response) {
    HttpParams params = new HttpParams();
    params.copyFrom(HttpRequestUtils.parseQueryString(request.getBody()));

    CreateUserDto createUserDto = CreateUserDto.of(params);

    try {
      userService.createUser(createUserDto);
    } catch (Exception e) {
      response.setStatus(HttpStatus.BAD_REQUEST);
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

package controller;

import annotation.PostMapping;
import http.HttpParams;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import service.UserService;
import utils.HttpRequestUtils;

public class LoginController implements Controller {
  private static final String MAPPING_URL = "/user/login";
  private final UserService userService = UserService.getInstance();


  @PostMapping
  public void login(HttpRequest request, HttpResponse response) {
    HttpParams bodyParams = request.getBody();

    boolean isSuccess = userService.login(bodyParams.getParam("userId"), bodyParams.getParam("password"));

    if (!isSuccess) {
      response.setStatus(HttpStatus.FOUND)
              .addHeader("Set-Cookie", "logined=false")
              .addHeader("Set-Cookie", "Path=/")
              .addHeader("Location", "/user/login_failed.html");
      return;
    }

    response.setStatus(HttpStatus.FOUND)
            .addHeader("Set-Cookie", "logined=true")
            .addHeader("Set-Cookie", "Path=/")
            .addHeader("Location", "/index.html");
  }


  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

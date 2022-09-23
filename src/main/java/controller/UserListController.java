package controller;

import annotation.GetMapping;
import http.HttpHeaders;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;

import java.util.List;

public class UserListController implements Controller {
  private static final String MAPPING_URL = "/user/list";

  @GetMapping
  public void showUserList(HttpRequest request, HttpResponse response) {
    HttpHeaders headers = request.getHeaders();

    List<String> cookies = headers.getHeader("Cookie");

    if (cookies == null || !cookies.contains("logined=true")) {
      response.setStatus(HttpStatus.FOUND)
              .addHeader("Location", "/user/login.html");
      return;
    }
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

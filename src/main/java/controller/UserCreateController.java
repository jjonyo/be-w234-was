package controller;

import annotation.PostMapping;
import http.HttpParams;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.nio.charset.StandardCharsets;

public class UserCreateController implements Controller {

  private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
  private static final String MAPPING_URL = "/user/create";

  @PostMapping
  public void createUser(HttpRequest request, HttpResponse response) {
    HttpParams params = new HttpParams();
    params.copyFrom(HttpRequestUtils.parseQueryString(request.getBody()));

    //TODO :: UserService 구현
    User user = User.of(params.getParam("userId"), params.getParam("password"), params.getParam("name"),
            params.getParam("email"));

    logger.debug(user.toString());

    response
            .setStatus(HttpStatus.FOUND)
            .addHeader("Location", "/index.html")
            .setBody("SignupSuccess".getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

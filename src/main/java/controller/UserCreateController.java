package controller;

import http.HttpParams;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class UserCreateController implements Controller {

  private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
  private static final String MAPPING_URL = "/user/create";

  @Override
  public void process(HttpRequest request, HttpResponse response) {
    HttpParams params = request.getParams();

    User user = User.of(params.getParam("userId"), params.getParam("password"), params.getParam("name"),
            params.getParam("email"));

    logger.debug(user.toString());

    response
            .setStatus(HttpStatus.OK)
            .setBody("SignupSuccess".getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

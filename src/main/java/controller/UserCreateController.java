package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCreateController implements Controller {

  private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
  private static final String MAPPING_URL = "/user/create";

  @Override
  public void process(HttpRequest request, HttpResponse response) {
    Map<String, String> params = request.getParams();

    User user = new User(params.get("userId"), params.get("password"), params.get("name"),
        params.get("email"));

    logger.debug(user.toString());

    response
        .setStatus(HttpStatus.OK)
        .setContentType("text/html", "charset=utf-8")
        .setBody("SignupSuccess".getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

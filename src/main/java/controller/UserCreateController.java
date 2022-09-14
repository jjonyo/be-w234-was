package controller;

import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.RequestHeaderParser.RequestHeader;

public class UserCreateController implements Controller{

  private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
  private static final String MAPPING_URL = "/user/create";

  @Override
  public byte[] process(RequestHeader requestHeader) {
    String queryString = requestHeader.getUrl().split("\\?")[1];
    Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);

    User user = new User(params.get("userId"), params.get("password"), params.get("name"),
        params.get("email"));

    logger.debug(user.toString());

    // TODO :: View를 반환하도록 구조 변경
    return "".getBytes();
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

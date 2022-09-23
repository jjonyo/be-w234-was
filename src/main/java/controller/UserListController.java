package controller;

import annotation.GetMapping;
import http.*;
import model.User;
import service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class UserListController implements Controller {
  private final UserService userService = UserService.getInstance();
  private static final String MAPPING_URL = "/user/list";
  private static final String USER_LIST_HTML_FILE_PATH = "webapp/user/list.html";

  @GetMapping
  public void showUserList(HttpRequest request, HttpResponse response) {
    HttpHeaders headers = request.getHeaders();

    List<String> cookies = headers.getHeader("Cookie");

    if (cookies == null || !cookies.contains("logined=true")) {
      response.setStatus(HttpStatus.FOUND)
              .addHeader("Location", "/user/login.html");
      return;
    }

    StringBuilder htmlStringBuilder = getHtmlStringBuilder();
    List<User> users = userService.findAll();
    appendUsers(htmlStringBuilder, users);

    response.setStatus(HttpStatus.OK)
            .setContentType(HttpContentType.TEXT_HTML.getValue())
            .setBody(htmlStringBuilder.toString().getBytes());
  }

  private void appendUsers(StringBuilder htmlStringBuilder, List<User> users) {
    String htmlString = htmlStringBuilder.toString();
    int userListIndex = htmlString.indexOf("user-list");
    int bracketIndex = htmlString.indexOf(">", userListIndex);

    int num = 0;
    for (User user : users) {
      num++;
      htmlStringBuilder.insert(bracketIndex + 1,
              String.format(
                              "                <tr>\n" +
                              "                    <th scope=\"row\">%s</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                              "                </tr>",
                      num, user.getUserId(), user.getName(), user.getEmail()
              )
      );
    }
  }

  private StringBuilder getHtmlStringBuilder() {
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new FileReader(USER_LIST_HTML_FILE_PATH));

      String line = br.readLine();
      while (line != null) {
        sb.append(line).append("\n");
        line = br.readLine();
      }
    } catch (Exception e) {
      throw new RuntimeException("HTML파일을 읽어오는데 실패했습니다.");
    }
    return sb;
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

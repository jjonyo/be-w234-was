package controller;

import annotation.GetMapping;
import http.HttpContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.UserService;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class UserListController implements Controller {
  private final UserService userService = UserService.getInstance();
  private static final String MAPPING_URL = "/user/list";
  private static final String USER_LIST_HTML_FILE_PATH = "webapp/user/list.html";

  @GetMapping
  public void showUserList(HttpRequest request, HttpResponse response) {
    boolean isLogin = "true".equals(request.getCookie("logined"));

    if (!isLogin) {
      response.setStatus(HttpStatus.FOUND)
              .setLocation("/user/login.html");
      return;
    }

    Document document;
    try {
      document = Jsoup.parse(new File(USER_LIST_HTML_FILE_PATH));
      appendUsers(document, userService.findAll());
    } catch (Exception e) {
      throw new RuntimeException("HTML 파싱에 실패했습니다.");
    }

    response.setStatus(HttpStatus.OK)
            .setContentType(HttpContentType.TEXT_HTML)
            .setBody(document.html().getBytes());
  }

  private void appendUsers(Document document, List<User> users) {
    Element element = document.getElementById("user-list");
    Objects.requireNonNull(element, "user-list 테이블을 찾을 수 없습니다.");

    int num = 0;
    for (User user : users) {
      num++;
      element.append(
              String.format(
                      "                <tr>\n" +
                              "                    <th scope=\"row\">%s</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                              "                </tr>",
                      num, user.getUserId(), user.getName(), user.getEmail()
              )
      );
    }
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

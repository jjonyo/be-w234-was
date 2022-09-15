package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontController {

  private static final Map<String, Controller> controllerMap = new HashMap<>();
  private static final String DEFAULT_HTML_DIRECTORY = "./webapp";
  private static final String DEFAULT_HTML = "/index.html";

  public FrontController() {
    init();
  }

  private void init() {
    List<Controller> controllers = List.of(new UserCreateController());

    for (Controller controller : controllers) {
      controllerMap.put(controller.getMappingUrl(), controller);
    }
  }

  public void process(HttpRequest request, HttpResponse response) throws IOException {
    String requestUrl = request.getRequestUrl();

    if (controllerMap.containsKey(requestUrl)) {
      Controller controller = controllerMap.get(requestUrl);
      controller.process(request, response);
      return;
    }

    File htmlFile = new File(DEFAULT_HTML_DIRECTORY + requestUrl);
    if (htmlFile.exists()) {
      List<String> contentTypes = request.getHeaders().getHeader("Content-Type");

      if (contentTypes != null) {
        response.setContentType(contentTypes.toArray(String[]::new));
      }

      response.setBody(htmlFile);
      return;
    }

    response
        .setStatus(HttpStatus.NOT_FOUND)
        .setBody("Not Found".getBytes(StandardCharsets.UTF_8));
  }
}

package controller;


import annotation.*;
import http.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontController {

  private static final Map<String, Controller> controllerMap = new HashMap<>();
  private static final String DEFAULT_HTML_DIRECTORY = "./webapp";

  public FrontController() {
    init();
  }

  private void init() {
    List<Controller> controllers = List.of(new SignUpController(), new LoginController());

    for (Controller controller : controllers) {
      controllerMap.put(controller.getMappingUrl(), controller);
    }
  }

  public void process(HttpRequest request, HttpResponse response) throws IOException {
    String requestUrl = request.getRequestUrl();

    Controller controller = controllerMap.getOrDefault(requestUrl, null);
    Method mappingMethod = getMappingMethod(controller, request.getMethod());

    if (mappingMethod != null) {
      try {
        mappingMethod.invoke(controller, request, response);
      } catch (Exception e) {
        throw new RuntimeException("Controller 실행에 실패했습니다.");
      }
      return;
    }

    File htmlFile = new File(DEFAULT_HTML_DIRECTORY + requestUrl);
    if (htmlFile.exists()) {
      String fileNameExtension = getFileNameExtension(htmlFile.getName());
      response.setContentType(HttpContentType.getContentTypeByExtension(fileNameExtension));
      response.setBody(htmlFile);
      return;
    }

    response
            .setStatus(HttpStatus.NOT_FOUND)
            .setBody("Not Found".getBytes(StandardCharsets.UTF_8));
  }

  private Method getMappingMethod(Controller controller, HttpMethod httpMethod) {
    if (controller == null) {
      return null;
    }

    Method[] declaredMethods = controller.getClass().getDeclaredMethods();
    for (Method method : declaredMethods) {
      if (isSupportMethod(httpMethod, method)) {
        return method;
      }
    }

    return null;
  }

  private boolean isSupportMethod(HttpMethod httpMethod, Method method) {
    switch (httpMethod) {
      case GET:
        return method.isAnnotationPresent(GetMapping.class);
      case POST:
        return method.isAnnotationPresent(PostMapping.class);
      case DELETE:
        return method.isAnnotationPresent(DeleteMapping.class);
      case PUT:
        return method.isAnnotationPresent(PutMapping.class);
      case PATCH:
        return method.isAnnotationPresent(PatchMapping.class);
      default:
        return false;
    }
  }

  private String getFileNameExtension(String fileName) {
    String extension = "";
    int i = fileName.lastIndexOf(".");

    if (i > 0) {
      extension = fileName.substring(i + 1);
    }

    return extension;
  }
}

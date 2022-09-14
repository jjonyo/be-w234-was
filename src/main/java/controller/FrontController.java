package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.RequestHeaderParser;
import utils.RequestHeaderParser.RequestHeader;

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

  public byte[] process(InputStream in) throws IOException {
    RequestHeader requestHeader = RequestHeaderParser.parse(in);
    String url = requestHeader.getUrl();
    String requestUrl = url.split("\\?")[0];

    if (controllerMap.containsKey(requestUrl)) {
      Controller controller = controllerMap.get(requestUrl);
      return controller.process(requestHeader);
    }

    File htmlFile = new File(DEFAULT_HTML_DIRECTORY + url);
    if (htmlFile.exists()) {
      return Files.readAllBytes(htmlFile.toPath());
    }

    return Files.readAllBytes(new File(DEFAULT_HTML_DIRECTORY + DEFAULT_HTML).toPath());
  }
}

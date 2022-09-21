package http;

public enum HttpContentType {
  APPLICATION_JSON("json", "application/json"),
  APPLICATION_JS("js", "application/javascript"),
  TEXT_HTML("html", "text/html"),
  TEXT_CSS("css", "text/css");

  private final String extension;
  private final String value;

  HttpContentType(String extension, String value) {
    this.extension = extension;
    this.value = value;
  }

  public static String getContentTypeByExtension(String extension) {
    String contentType = "";

    for (HttpContentType type : values()) {
      if (type.extension.equals(extension)) {
        contentType = type.value;
      }
    }

    return contentType;
  }

  public String getValue() {
    return value;
  }
}

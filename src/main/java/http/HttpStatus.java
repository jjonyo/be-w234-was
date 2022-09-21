package http;

public enum HttpStatus {
  OK(200, "OK"),
  FOUND(302, "Found"),
  BAD_REQUEST(400, "Bad Request"),
  NOT_FOUND(404, "Not Found");

  private final int code;
  private final String message;

  HttpStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int code() {
    return code;
  }

  public String message() {
    return message;
  }
}

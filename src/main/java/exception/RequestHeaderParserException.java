package exception;

public class RequestHeaderParserException extends RuntimeException {

  public RequestHeaderParserException() {
    super();
  }

  public RequestHeaderParserException(String message) {
    super(message);
  }

  public RequestHeaderParserException(String message, Throwable cause) {
    super(message, cause);
  }

  public RequestHeaderParserException(Throwable cause) {
    super(cause);
  }
}

package exception;

public class HttpRequestException extends RuntimeException{

  public HttpRequestException() {
    super();
  }

  public HttpRequestException(String message) {
    super(message);
  }

  public HttpRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public HttpRequestException(Throwable cause) {
    super(cause);
  }
}

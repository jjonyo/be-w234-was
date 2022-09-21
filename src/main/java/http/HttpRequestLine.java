package http;

public class HttpRequestLine {

  private final HttpMethod method;
  private final String url;
  private final String requestUrl;
  private final String version;

  private HttpRequestLine(String method, String url, String requestUrl, String version) {
    this.method = HttpMethod.valueOf(method);
    this.url = url;
    this.requestUrl = requestUrl;
    this.version = version;
  }

  public static HttpRequestLine of(String method, String url, String requestUrl, String version) {
    return new HttpRequestLine(method, url, requestUrl, version);
  }

  public HttpMethod getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public String getVersion() {
    return version;
  }
}

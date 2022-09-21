package http;

import java.io.InputStream;

public class HttpRequest {

  private final HttpRequestLine requestLine;
  private final HttpHeaders headers;
  private final HttpParams params;
  //private final HttpBody body;


  private HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, HttpParams params) {
    this.requestLine = requestLine;
    this.headers = headers;
    this.params = params;
  }

  public static HttpRequest of(InputStream in) {
    HttpRequestParser httpRequestParser = new HttpRequestParser(in);

    HttpRequestLine requestLine = httpRequestParser.parseRequestLine();
    HttpHeaders headers = httpRequestParser.parseRequestHeaders();
    HttpParams params = httpRequestParser.parseRequestParams(requestLine);

    return new HttpRequest(requestLine, headers, params);
  }

  public HttpRequestLine getRequestLine() {
    return requestLine;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public HttpParams getParams() {
    return params;
  }

  public String getRequestUrl() {
    return requestLine.getRequestUrl();
  }

  public String getMethod() {
    return requestLine.getMethod();
  }

  public String getUrl() {
    return requestLine.getUrl();
  }

  public String getVersion() {
    return requestLine.getVersion();
  }
}

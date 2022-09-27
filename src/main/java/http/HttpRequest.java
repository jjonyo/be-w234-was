package http;

import java.io.InputStream;

public class HttpRequest {

  private final HttpRequestLine requestLine;
  private final HttpHeaders headers;
  private final HttpParams params;
  private final HttpParams body;
  private final HttpCookie cookie;


  private HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, HttpParams params, HttpParams body, HttpCookie cookie) {
    this.requestLine = requestLine;
    this.headers = headers;
    this.params = params;
    this.body = body;
    this.cookie = cookie;
  }

  public static HttpRequest of(InputStream in) {
    HttpRequestParser httpRequestParser = new HttpRequestParser(in);

    HttpRequestLine requestLine = httpRequestParser.parseRequestLine();
    HttpHeaders headers = httpRequestParser.parseRequestHeaders();
    HttpParams params = httpRequestParser.parseRequestParams(requestLine);
    HttpParams body = httpRequestParser.parseRequestBody(headers.getHeader(HttpHeaders.CONTENT_LENGTH));
    HttpCookie cookie = httpRequestParser.parseRequestCookie(headers.getHeader(HttpHeaders.COOKIE));

    return new HttpRequest(requestLine, headers, params, body, cookie);
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

  public HttpMethod getMethod() {
    return requestLine.getMethod();
  }

  public String getUrl() {
    return requestLine.getUrl();
  }

  public String getVersion() {
    return requestLine.getVersion();
  }

  public HttpParams getBody() {
    return body;
  }

  public String getCookie(String key) {
    return cookie.getCookie(key);
  }
}

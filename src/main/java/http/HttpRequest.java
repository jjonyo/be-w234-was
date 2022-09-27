package http;

import java.io.InputStream;

public class HttpRequest {

  private final HttpRequestLine requestLine;
  private final HttpHeaders headers;
  private final HttpParams params;
  private final HttpParams body;


  private HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, HttpParams params, HttpParams body) {
    this.requestLine = requestLine;
    this.headers = headers;
    this.params = params;
    this.body = body;
  }

  public static HttpRequest of(InputStream in) {
    HttpRequestParser httpRequestParser = new HttpRequestParser(in);

    HttpRequestLine requestLine = httpRequestParser.parseRequestLine();
    HttpHeaders headers = httpRequestParser.parseRequestHeaders();
    HttpParams params = httpRequestParser.parseRequestParams(requestLine);
    HttpParams body = httpRequestParser.parseRequestBody(headers.getHeader("Content-Length"));

    return new HttpRequest(requestLine, headers, params, body);
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
}

package http;

import exception.HttpRequestException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

  private final String method;
  private final String url;
  private final String version;
  private final HttpHeaders headers;
  //private final HttpBody body;


  private HttpRequest(String method, String url, String version, HttpHeaders headers) {
    this.method = method;
    this.url = url;
    this.version = version;
    this.headers = headers;
  }

  public static HttpRequest of(InputStream in) {
    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    String method, url, version;

    try {
      String[] requestLine = br.readLine().split(" ");
      method = requestLine[0];
      url = requestLine[1];
      version = requestLine[2];
    } catch (Exception e) {
      throw new HttpRequestException("HttpRequest 생성에 실패했습니다.");
    }

    HttpHeaders headers = parseHeaders(br);

    return new HttpRequest(method, url, version, headers);
  }

  public void addHeader(String key, String value) {
    headers.addHeader(key, value);
  }

  private static HttpHeaders parseHeaders(BufferedReader br) {
    HttpHeaders headers = new HttpHeaders();

    try {
      String line = br.readLine();
      while (!"".equals(line)) {
        String[] splitLine = line.split(": ");
        headers.addHeader(splitLine[0], splitLine[1]);

        line = br.readLine();
        if (line == null) {
          break;
        }
      }
    } catch(Exception e) {
      throw new HttpRequestException("HttpRequest 생성에 실패했습니다.");
    }

    return headers;
  }

  public String getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public String getVersion() {
    return version;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }
}

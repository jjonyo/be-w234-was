package http;

import exception.HttpRequestException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import utils.HttpRequestUtils;

public class HttpRequest {

  private final String method;
  private final String url;
  private final String requestUrl;
  private final String version;
  private final HttpHeaders headers;
  private final Map<String, String> params;
  //private final HttpBody body;

  private static final String QUERY_SPLIT_STRING = "\\?";


  private HttpRequest(String method, String url, String version, HttpHeaders headers, Map<String, String> params) {
    this.method = method;
    this.url = url;
    this.requestUrl = url.split(QUERY_SPLIT_STRING)[0];
    this.version = version;
    this.headers = headers;
    this.params = params;
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
    Map<String, String> params = parseParams(url);

    return new HttpRequest(method, url, version, headers, params);
  }

  private static Map<String, String> parseParams(String url) {
    String[] splitUrl = url.split(QUERY_SPLIT_STRING);

    if (splitUrl.length > 1) {
      return HttpRequestUtils.parseQueryString(splitUrl[1]);
    }

    return new HashMap<>();
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
    } catch (Exception e) {
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

  public String getRequestUrl() {
    return requestUrl;
  }

  public String getVersion() {
    return version;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public Map<String, String> getParams() {
    return params;
  }
}

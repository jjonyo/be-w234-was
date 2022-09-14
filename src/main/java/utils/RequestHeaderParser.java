package utils;

import exception.RequestHeaderParserException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RequestHeaderParser {

  private RequestHeaderParser() {}

  public static RequestHeader parse(InputStream in){
    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    String method, url, version;

    try {
      String firstLine = br.readLine();
      String[] splitFirstLine = firstLine.split(" ");

      method = splitFirstLine[0];
      url = splitFirstLine[1];
      version = splitFirstLine[2];
    } catch (Exception e) {
      throw new RequestHeaderParserException("Request Header 파싱에 실패했습니다.");
    }

    return RequestHeader.of(method, url, version);
  }

  public static class RequestHeader {

    private final String method;
    private final String url;
    private final String version;

    private RequestHeader(String method, String url, String version) {
      this.method = method;
      this.url = url;
      this.version = version;
    }

    public static RequestHeader of(String method, String url, String version) {
      return new RequestHeader(method, url, version);
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
  }
}

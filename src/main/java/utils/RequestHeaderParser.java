package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestHeaderParser {

  private RequestHeaderParser() {}

  public static RequestHeader parse(InputStream in) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String method, url, version;

    try {
      String firstLine = br.readLine();
      String[] splitFirstLine = firstLine.split(" ");

      method = splitFirstLine[0];
      url = splitFirstLine[1];
      version = splitFirstLine[2];
    } catch (IndexOutOfBoundsException e) {
      throw new RuntimeException("Request Header 파싱에 실패했습니다.");
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

package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParserTest {

  String HttpRequestString = "GET /index.html HTTP/1.1\n" +
          "Host: localhost:8080\n" +
          "Connection: keep-alive\n" +
          "Accept: */*";

  String HttpRequestStringWithQuery = "GET /user/create?userId=test&password=test&name=test HTTP/1.1\n" +
          "Host: localhost:8080\n" +
          "Connection: keep-alive\n" +
          "Accept: */*";

  String httpRequestStringWithBody = "POST /user/create HTTP/1.1\n" +
          "Host: localhost:8080\n" +
          "Connection: keep-alive\n" +
          "Content-Length: 59\n" +
          "Content-Type: application/x-www-form-urlencoded\n" +
          "Accept: */*\n" +
          "\n" +
          "userId=test&password=password&name=test&email=test@test.net";

  private HttpRequestParser httpRequestParser;

  @Test
  @DisplayName("HttpRequestLine 파싱에 성공해야 한다.")
  void parseRequestLine() {
    httpRequestParser = new HttpRequestParser(new ByteArrayInputStream(HttpRequestString.getBytes()));

    HttpRequestLine httpRequestLine = httpRequestParser.parseRequestLine();

    assertThat(httpRequestLine.getRequestUrl()).isEqualTo("/index.html");
    assertThat(httpRequestLine.getUrl()).isEqualTo("/index.html");
    assertThat(httpRequestLine.getMethod()).isEqualTo(HttpMethod.GET);
    assertThat(httpRequestLine.getVersion()).isEqualTo("HTTP/1.1");
  }

  @Test
  @DisplayName("HttpRequestHeaders 파싱에 성공해야 한다.")
  void parseHeaders() {
    httpRequestParser = new HttpRequestParser(new ByteArrayInputStream(HttpRequestString.getBytes()));

    httpRequestParser.parseRequestLine();
    HttpHeaders headers = httpRequestParser.parseRequestHeaders();

    assertThat(headers.getHeader("Host")).contains("localhost:8080");
    assertThat(headers.getHeader("Connection")).contains("keep-alive");
  }

  @Test
  @DisplayName("HttpRequestParams 파싱에 성공해야 한다.")
  void parseParams() {
    httpRequestParser = new HttpRequestParser(new ByteArrayInputStream(HttpRequestStringWithQuery.getBytes()));

    HttpParams params = httpRequestParser.parseRequestParams(httpRequestParser.parseRequestLine());

    assertThat(params.getParam("userId")).contains("test");
    assertThat(params.getParam("password")).contains("test");
    assertThat(params.getParam("name")).contains("test");
  }

  @Test
  @DisplayName("HttpBody 파싱에 성공해야 한다.")
  void parseBody() {
    httpRequestParser = new HttpRequestParser(new ByteArrayInputStream(httpRequestStringWithBody.getBytes()));

    httpRequestParser.parseRequestLine();
    HttpHeaders headers = httpRequestParser.parseRequestHeaders();
    HttpParams bodyParams = httpRequestParser.parseRequestBody(headers.getHeader("Content-Length"));

    assertThat(bodyParams.getParam("userId")).isEqualTo("test");
    assertThat(bodyParams.getParam("password")).isEqualTo("password");
    assertThat(bodyParams.getParam("name")).isEqualTo("test");
    assertThat(bodyParams.getParam("email")).isEqualTo("test@test.net");
    assertThat(bodyParams.getParam("x")).isNull();
  }
}
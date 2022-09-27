package http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.HttpRequestException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

  @Test
  @DisplayName("HttpRequest 생성에 성공해야 한다.")
  void httpRequestCreate() {
    //given
    String HttpRequestString = "GET /index.html HTTP/1.1\n" +
        "Host: localhost:8080\n" +
        "Connection: keep-alive\n" +
        "Accept: */*";
    InputStream in = new ByteArrayInputStream(HttpRequestString.getBytes(StandardCharsets.UTF_8));

    //when
    HttpRequest request = HttpRequest.of(in);

    //then
    assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
    assertThat(request.getUrl()).isEqualTo("/index.html");
    assertThat(request.getVersion()).isEqualTo("HTTP/1.1");
    assertThat(request.getHeaders().getHeader("Host")).contains("localhost:8080");
    assertThat(request.getHeaders().getHeader("Connection")).contains("keep-alive");
  }

  @Test
  @DisplayName("요청이 잘못되었을 경우 HttpRequest 생성 실패해야 한다.")
  void failHttpRequestCreate() {
    //given
    String HttpRequestString = "GET /index.html\n" +
        "Host: localhost:8080\n" +
        "Connection: keep-alive\n" +
        "Accept: */*";
    InputStream in = new ByteArrayInputStream(HttpRequestString.getBytes(StandardCharsets.UTF_8));

    //when-then
    assertThrows(HttpRequestException.class, () -> HttpRequest.of(in));
  }

  @Test
  @DisplayName("params가 있을 경우 파싱할 수 있어야 합니다.")
  void parseParams() {
    //given
    String HttpRequestString = "GET /user/create?userId=test&password=test&name=test HTTP/1.1\n" +
        "Host: localhost:8080\n" +
        "Connection: keep-alive\n" +
        "Accept: */*";
    InputStream in = new ByteArrayInputStream(HttpRequestString.getBytes(StandardCharsets.UTF_8));

    //when
    HttpRequest request = HttpRequest.of(in);

    //then
    assertThat(request.getParams().getParam("userId")).isEqualTo("test");
  }

  @Test
  @DisplayName("HttpRequest Body 파싱에 성공해야 한다.")
  void parseBody() {
    String httpRequestString = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=test&password=password&name=test&email=test@test.net";
    InputStream in = new ByteArrayInputStream(httpRequestString.getBytes(StandardCharsets.UTF_8));

    HttpRequest request = HttpRequest.of(in);

    HttpParams bodyParams = request.getBody();

    assertThat(bodyParams.getParam("userId")).isEqualTo("test");
    assertThat(bodyParams.getParam("password")).isEqualTo("password");
    assertThat(bodyParams.getParam("name")).isEqualTo("test");
    assertThat(bodyParams.getParam("email")).isEqualTo("test@test.net");
    assertThat(bodyParams.getParam("x")).isNull();
  }
}

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
    assertThat(request.getMethod()).isEqualTo("GET");
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
}

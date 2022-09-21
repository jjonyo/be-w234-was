package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {

  @Test
  @DisplayName("Header추가가 정상적으로 되어야 한다.")
  void addHeader() {
    HttpHeaders headers = new HttpHeaders();

    headers.addHeader("Content-Type", "text/html");
    headers.addHeader("Content-Type", "charset=utf-8");

    assertThat(headers.getHeader("Content-Type")).contains("text/html", "charset=utf-8");
  }

  @Test
  @DisplayName("Header가 존재하지 않을 시 null을 반환해야 한다.")
  void getHeaderWhenNull() {
    HttpHeaders headers = new HttpHeaders();

    assertThat(headers.getHeader("Content-Type")).isNull();
  }
}
package utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import exception.RequestHeaderParserException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestHeaderParser.RequestHeader;

public class RequestHeaderParserTest {

  @Test
  @DisplayName("InputStream으로 부터 헤더를 파싱할 수 있어야한다.")
  void parse() throws IOException {
    //given
    String headerString = "GET /index.html HTTP/1.1";
    InputStream in = new ByteArrayInputStream(headerString.getBytes(StandardCharsets.UTF_8));

    //when
    RequestHeader requestHeader = RequestHeaderParser.parse(in);

    //then
    assertThat(requestHeader.getMethod()).isEqualTo("GET");
    assertThat(requestHeader.getUrl()).isEqualTo("/index.html");
    assertThat(requestHeader.getVersion()).isEqualTo("HTTP/1.1");
  }

  @Test
  @DisplayName("Header가 잘못된 경우 파싱에 실패해야 한다.")
  void parseFail() {
    String headerString = "GET";
    InputStream in = new ByteArrayInputStream(headerString.getBytes(StandardCharsets.UTF_8));

    assertThrows(RequestHeaderParserException.class,
        () -> RequestHeaderParser.parse(in));
  }
}

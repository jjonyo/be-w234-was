package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

  OutputStream getOutputStream() {
    return new OutputStream() {
      @Override
      public void write(int b) throws IOException {
      }
    };
  }

  @Test
  @DisplayName("HttpResponse 생성")
  void createHttpResponse() {
    OutputStream out = getOutputStream();

    HttpResponse httpResponse = HttpResponse.of(out);

    assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.OK);
    assertThat(httpResponse.getHeaders()).isNotNull();
  }

  @Test
  @DisplayName("HttpStatus 변경")
  void setHttpStatus() {
    OutputStream out = getOutputStream();
    HttpResponse httpResponse = HttpResponse.of(out);

    httpResponse.setStatus(HttpStatus.FOUND);
    httpResponse.setStatus(HttpStatus.BAD_REQUEST);

    assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @DisplayName("HttpHeader 추가")
  void addHttpHeader() {
    OutputStream out = getOutputStream();
    HttpResponse httpResponse = HttpResponse.of(out);

    httpResponse.addHeader("Custom-Header", "customHeader");
    HttpHeaders headers = httpResponse.getHeaders();

    assertThat(headers.getHeader("Custom-Header")).contains("customHeader");
  }

  @Test
  @DisplayName("Http ContentType 추가")
  void setContentType() {
    OutputStream out = getOutputStream();
    HttpResponse httpResponse = HttpResponse.of(out);

    httpResponse.setContentType(HttpContentType.TEXT_HTML);
    httpResponse.setContentType(HttpContentType.TEXT_CSS);
    HttpHeaders headers = httpResponse.getHeaders();

    assertThat(headers.getHeader(HttpHeaders.CONTENT_TYPE))
            .contains(HttpContentType.TEXT_HTML.getValue(), HttpContentType.TEXT_CSS.getValue());
  }

  @Test
  @DisplayName("Http Cookie 추가")
  void setCookie() {
    OutputStream out = getOutputStream();
    HttpResponse httpResponse = HttpResponse.of(out);
    String cookieString = "logined=true";
    String[] splitCookieString = cookieString.split("=");

    httpResponse.addCookie(splitCookieString[0], splitCookieString[1]);
    HttpHeaders headers = httpResponse.getHeaders();

    assertThat(headers.getHeader(HttpHeaders.SET_COOKIE)).contains(cookieString);
  }
}
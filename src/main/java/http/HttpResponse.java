package http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {

  private final DataOutputStream dos;
  private HttpStatus status;
  private HttpHeaders headers;
  private byte[] body;

  private HttpResponse(OutputStream out) {
    this.dos = new DataOutputStream(out);
    this.status = HttpStatus.OK;
    this.headers = new HttpHeaders();
  }

  public static HttpResponse of(OutputStream out) {
    return new HttpResponse(out);
  }

  public HttpResponse setStatus(HttpStatus status) {
    this.status = status;

    return this;
  }

  public HttpResponse setContentType(String... values) {
    for (String value : values) {
      addHeader(HttpHeaders.CONTENT_TYPE, value);
    }

    return this;
  }

  public HttpResponse setContentType(HttpContentType... types) {
    for (HttpContentType type : types) {
      addHeader(HttpHeaders.CONTENT_TYPE, type.getValue());
    }

    return this;
  }

  public HttpResponse addHeader(String key, String value) {
    this.headers.addHeader(key, value);

    return this;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }

  public void setBody(File file) throws IOException {
    this.body = Files.readAllBytes(file.toPath());
  }

  public HttpResponse setLocation(String location) {
    addHeader(HttpHeaders.LOCATION, location);

    return this;
  }

  public void write() {
    try {
      dos.writeBytes("HTTP/1.1 " + status.code() + " " + status.message() + "\r\n");
      writeHeaders();
      if (body != null) {
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
      }
      dos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeHeaders() {
    headers.map().forEach(
            (key, values) -> {
              String joinString = String.join(";", values);
              try {
                dos.writeBytes(key + ": " + joinString + "\r\n");
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
    );
  }
}

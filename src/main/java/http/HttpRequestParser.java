package http;

import exception.HttpRequestException;
import utils.HttpRequestUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestParser {

  private final BufferedReader br;
  private static final String QUERY_SPLIT_STRING = "\\?";

  public HttpRequestParser(InputStream in) {
    this.br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
  }

  public HttpRequestLine parseRequestLine() {
    String method, url, requestUrl, version;

    try {
      String[] requestLine = br.readLine().split(" ");
      method = requestLine[0];
      url = requestLine[1];
      requestUrl = url.split(QUERY_SPLIT_STRING)[0];
      version = requestLine[2];
    } catch (Exception e) {
      throw new HttpRequestException("HttpRequestLine 파싱에 실패했습니다.");
    }

    return HttpRequestLine.of(method, url, requestUrl, version);
  }

  public HttpHeaders parseRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();

    try {
      String line = br.readLine();
      while (!line.isEmpty()) {
        String[] splitLine = line.split(": ");
        headers.addHeader(splitLine[0], splitLine[1]);

        line = br.readLine();
        if (line == null) {
          break;
        }
      }
    } catch (Exception e) {
      throw new HttpRequestException("HttpRequestHeader 생성에 실패했습니다.");
    }

    return headers;
  }

  public HttpParams parseRequestParams(HttpRequestLine requestLine) {
    HttpParams params = new HttpParams();

    String[] splitUrl = requestLine.getUrl().split(QUERY_SPLIT_STRING);

    if (splitUrl.length > 1) {
      params.copyFrom(HttpRequestUtils.parseQueryString(splitUrl[1]));
    }

    return params;
  }

  public String parseRequestBody(int contentLength) {
    try {
      return IOUtils.readData(br, contentLength);
    } catch(Exception e) {
      throw new HttpRequestException("HttpRequest Body 파싱에 실패했습니다.");
    }
  }
}

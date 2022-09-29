package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
  public static final String CONTENT_LENGTH = "Content-Length";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String COOKIE = "Cookie";
  public static final String SET_COOKIE = "Set-Cookie";
  public static final String LOCATION = "Location";

  private final Map<String, List<String>> headers = new HashMap<>();

  public void addHeader(String key, String value) {
    if (!headers.containsKey(key)) {
      headers.put(key, new ArrayList<>());
    }

    headers.get(key).add(value);
  }

  public List<String> getHeader(String key) {
    return headers.getOrDefault(key, null);
  }

  public Map<String, List<String>> map() {
    return headers;
  }
}

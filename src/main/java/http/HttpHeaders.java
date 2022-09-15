package http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

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

  public Map<String, List<String>> map() { return headers; }
}

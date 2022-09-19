package http;

import java.util.HashMap;
import java.util.Map;

public class HttpParams {

  private final Map<String, String> params = new HashMap<>();

  public void addParam(String key, String value) {
    params.put(key, value);
  }

  public void removeParam(String key) {
    params.remove(key);
  }

  public String getParam(String key) {
    return params.getOrDefault(key, null);
  }

  public Map<String, String> map() {
    return params;
  }
}

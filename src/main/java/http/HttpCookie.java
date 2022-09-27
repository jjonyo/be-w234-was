package http;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
  private final Map<String, String> cookies;

  public HttpCookie() {
    this.cookies = new HashMap<>();
  }

  public static HttpCookie from(Map<String, String> map) {
    HttpCookie cookie = new HttpCookie();

    cookie.map().putAll(map);

    return cookie;
  }

  public String getCookie(String key) {
    return cookies.getOrDefault(key, "");
  }

  public Map<String, String> map() {
    return cookies;
  }
}

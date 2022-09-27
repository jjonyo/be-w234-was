package controller;

import annotation.PostMapping;
import controller.dto.CreateMemoDto;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import service.MemoService;

public class MemoController implements Controller {
  private static final String MAPPING_URL = "/memo";
  private final MemoService memoService = MemoService.getInstance();

  @PostMapping
  public void createMemo(HttpRequest request, HttpResponse response) {
    boolean isLogin = "true".equals(request.getCookie("logined"));

    if (!isLogin) {
      response.setStatus(HttpStatus.FOUND)
              .setLocation("/user/login.html");
      return;
    }

    try {
      CreateMemoDto createMemoDto = CreateMemoDto.of(request.getBody());
      memoService.saveMemo(createMemoDto);
    } catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpStatus.BAD_REQUEST)
              .setBody("Create memo fail".getBytes());
    }

    response.setStatus(HttpStatus.FOUND)
            .setLocation("/index.html");
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

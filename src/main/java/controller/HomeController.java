package controller;

import annotation.GetMapping;
import http.HttpContentType;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.Memo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.MemoService;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class HomeController implements Controller {
  private static final String MAPPING_URL = "/home";
  private static final String HOME_HTML_FILE_PATH = "webapp/index.html";
  private final MemoService memoService = MemoService.getInstance();

  @GetMapping
  public void home(HttpRequest request, HttpResponse response) {
    Document document;
    try {
      document = Jsoup.parse(new File(HOME_HTML_FILE_PATH));
      appendMemoList(document, memoService.getAllMemo());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("HTML 파싱에 실패했습니다.");
    }

    response.setStatus(HttpStatus.OK)
            .setContentType(HttpContentType.TEXT_HTML)
            .setBody(document.html().getBytes());
  }

  private void appendMemoList(Document document, List<Memo> memoList) {
    Element element = document.getElementById("memo-list");
    Objects.requireNonNull(element, "memo-list 태그를 찾을 수 없습니다.");

    for (Memo memo : memoList) {
      element.append(String.format("<li>\n" +
                              "<div class=\"wrap\">\n" +
                              "<div class=\"main\">\n" +
                              "<strong class=\"subject\">\n" +
                              "<a href=\"./qna/show.html\">%s</a>\n" +
                              "</strong>\n" +
                              "<div class=\"auth-info\">\n" +
                              "<i class=\"icon-add-comment\"></i>\n" +
                              "<span class=\"time\">%s</span>\n" +
                              "<a href=\"./user/profile.html\" class=\"author\">%s</a>\n" +
                              "</div>\n" +
                              "<div class=\"reply\" title=\"댓글\">\n" +
                              "<i class=\"icon-reply\"></i>\n" +
                              "<span class=\"point\">8</span>\n" +
                              "</div>\n" +
                              "</div>\n" +
                              "</div>\n" +
                              "</li>"
                      ,
                      memo.getTitle(),
                      memo.getCreatedAt().toString(),
                      memo.getAuthor()
              )
      );
    }
  }

  @Override
  public String getMappingUrl() {
    return MAPPING_URL;
  }
}

package controller.dto;

import http.HttpParams;

public class CreateMemoDto {
  private final String author;
  private final String title;
  private final String contents;

  private CreateMemoDto(String author, String title, String contents) {
    this.author = author;
    this.title = title;
    this.contents = contents;
  }

  public static CreateMemoDto of(HttpParams bodyParams) {
    return new CreateMemoDto(bodyParams.getParam("author"), bodyParams.getParam("title"), bodyParams.getParam("contents"));
  }

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public String getContents() {
    return contents;
  }
}

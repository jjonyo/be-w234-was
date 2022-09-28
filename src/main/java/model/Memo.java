package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Memo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String author;

  private String title;

  private String contents;

  @CreationTimestamp
  private LocalDateTime createdAt;

  protected Memo() {}

  private Memo(String author, String title, String content) {
    this.author = author;
    this.title = title;
    this.contents = content;
  }

  public static Memo of(String author, String title, String contents) {
    return new Memo(author, title, contents);
  }

  public Long getId() {
    return id;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}

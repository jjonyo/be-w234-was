package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

  User getUser() {
    return User.of("userId", "password", "name", "email@email.com");
  }

  @Test
  @DisplayName("유저 생성 테스트")
  void createUser() {
    User user = getUser();

    assertThat(user.getUserId()).isEqualTo("userId");
    assertThat(user.getPassword()).isEqualTo("password");
    assertThat(user.getName()).isEqualTo("name");
    assertThat(user.getEmail()).isEqualTo("email@email.com");
  }

  @Test
  @DisplayName("isCorrectPassword 테스트")
  void isCorrectPassword() {
    User user = getUser();

    assertThat(user.isCorrectPassword("password")).isTrue();
  }
}
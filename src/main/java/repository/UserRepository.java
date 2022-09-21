package repository;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  Optional<User> save(User user);

  Optional<User> findByUserId(String id);

  List<User> findAll();

  boolean delete(User user);
}

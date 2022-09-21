package repository;

import exception.UserException;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
  private static final Map<String, User> userMap = new ConcurrentHashMap<>();

  @Override
  public Optional<User> save(User user) {
    if (userMap.containsKey(user.getUserId())) {
      throw new UserException("이미 존재하는 유저Id입니다. id:" + user.getUserId());
    }

    userMap.put(user.getUserId(), user);

    return Optional.of(user);
  }

  @Override
  public Optional<User> findByUserId(String id) {
    return Optional.ofNullable(userMap.get(id));
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(userMap.values());
  }

  @Override
  public boolean delete(User user) {
    User removedUser = userMap.remove(user.getUserId());

    return removedUser != null;
  }
}

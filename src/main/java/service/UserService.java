package service;

import controller.dto.CreateUserDto;
import model.User;
import repository.InMemoryUserRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

  private static final UserService instance = new UserService();
  private final UserRepository userRepository;

  private UserService() {
    this.userRepository = new InMemoryUserRepository();
  }

  public static UserService getInstance() {
    return instance;
  }

  public void createUser(CreateUserDto createUserDto) {
    User user = User.of(
            createUserDto.getUserId(),
            createUserDto.getPassword(),
            createUserDto.getName(),
            createUserDto.getEmail()
    );

    userRepository.save(user);
  }

  public boolean login(String userId, String password) {
    Optional<User> user = userRepository.findByUserId(userId);

    if (user.isEmpty()) {
      return false;
    }

    String userPassword = user.get().getPassword();
    return userPassword.equals(password);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }
}

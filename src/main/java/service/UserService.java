package service;

import controller.dto.CreateUserDto;
import model.User;
import repository.UserRepository;

public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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
}

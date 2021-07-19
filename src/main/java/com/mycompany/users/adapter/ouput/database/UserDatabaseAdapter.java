package com.mycompany.users.adapter.ouput.database;

import static reactor.core.publisher.Mono.fromCallable;

import com.mycompany.users.adapter.ouput.database.mapper.UserDbMapper;
import com.mycompany.users.adapter.ouput.database.repository.UserRepository;
import com.mycompany.users.domain.port.output.UserPort;
import com.mycompany.users.entity.User;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserDatabaseAdapter implements UserPort {

  private final UserDbMapper userDbMapper;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public Mono<User> createUser(User user) {
    return fromCallable(() -> userDbMapper.convert(user))
        .map(userRepository::save)
        .thenReturn(user);
  }
}

package com.mycompany.users.domain.port.input;

import com.mycompany.users.entity.User;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {

  Mono<User> createUser(User user);
}

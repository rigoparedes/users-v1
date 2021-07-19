package com.mycompany.users.domain.port.output;

import com.mycompany.users.entity.User;
import reactor.core.publisher.Mono;

public interface UserPort {

  Mono<User> createUser(User user);
}

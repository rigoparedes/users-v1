package com.mycompany.users.domain.interactor;

import com.mycompany.users.domain.port.input.CreateUserUseCase;
import com.mycompany.users.domain.port.output.UserPort;
import com.mycompany.users.domain.util.ExceptionUtils;
import com.mycompany.users.entity.User;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserInteractor implements CreateUserUseCase {

  private final ExceptionUtils exceptionUtils;
  private final UserPort userPort;

  @Override
  public Mono<User> createUser(User user) {
    return Mono.fromCallable(() -> fillUserInformation(user))
        .flatMap(userPort::createUser)
        .onErrorMap(exceptionUtils::toUserApiException);
  }

  private User fillUserInformation(User user) {
    OffsetDateTime now = OffsetDateTime.now();
    return user.toBuilder()
        .id(UUID.randomUUID())
        .created(now)
        .lastLogin(now)
        .active(true)
        .build();
  }
}

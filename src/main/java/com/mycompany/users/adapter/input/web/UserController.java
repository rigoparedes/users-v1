package com.mycompany.users.adapter.input.web;

import com.mycompany.users.adapter.input.web.mapper.UserCreationResponseMapper;
import com.mycompany.users.adapter.input.web.mapper.UserMapper;
import com.mycompany.users.adapter.input.web.model.UserCreationRequest;
import com.mycompany.users.adapter.input.web.model.UserCreationResponse;
import com.mycompany.users.domain.port.input.CreateUserUseCase;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("${openapi.users.base-path}")
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserMapper userMapper;
  private final UserCreationResponseMapper userCreationResponseMapper;
  private final CreateUserUseCase createUserUseCase;

  @Override
  public Mono<ResponseEntity<UserCreationResponse>> createUser(
      @Valid @RequestBody Mono<UserCreationRequest> request, final ServerWebExchange exchange) {
    return request
        .map(userMapper::convert)
        .doOnSuccess(it -> it.setToken(this.getToken(exchange)))
        .flatMap(createUserUseCase::createUser)
        .map(userCreationResponseMapper::convert)
        .map(it -> new ResponseEntity<>(it, HttpStatus.CREATED));
  }

  private String getToken(ServerWebExchange exchange) {
    return Optional.ofNullable(exchange.getRequest().getHeaders())
        .map(it -> it.get("Authorization"))
        .filter(it -> !it.isEmpty())
        .map(CollectionUtils::firstElement)
        .map(it -> it.replace("Bearer ", ""))
        // TODO: JWT mandatory
        .orElse("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJoaEBteWNvbXBhbnkuY29tIn0=.Z");
  }
}

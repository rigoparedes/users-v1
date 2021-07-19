package com.mycompany.users.domain.util;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
public class ExceptionUtils {

  private final Map<ErrorCode, UserApplicationProperties.ErrorProperties> errors;

  public ExceptionUtils(List<UserApplicationProperties.ErrorProperties> properties) {
    errors = properties.stream().collect(toMap(it -> ErrorCode.valueOf(it.getCode()), it -> it));
  }

  public UserApiException toUserApiException(Throwable exception) {
    return ofNullable(exception)
        .filter(DataIntegrityViolationException.class::isInstance)
        .map(it -> it.getCause())
        .filter(Objects::nonNull)
        .filter(ConstraintViolationException.class::isInstance)
        .map(ConstraintViolationException.class::cast)
        // TODO: Validate database index name
        .filter(it -> it.getErrorCode() == 23505)
        .map(it -> ErrorCode.EMAIL_REPEATED_ERROR)
        .map(this::buildUserApiException)
        // TODO: Custom error message
        // .orElseGet(() -> this.buildUserApiException(ErrorCode.UNEXPECTED_ERROR));
        .orElseGet(() -> this.buildUnexpectedException(exception));
  }

  private UserApiException buildUnexpectedException(Throwable throwable) {
    return UserApiException.builder()
        .status(errors.get(ErrorCode.UNEXPECTED_ERROR).getStatus())
        // Original error message
        .message(throwable.getMessage())
        .build();
  }

  private UserApiException buildUserApiException(ErrorCode errorCode) {
    return UserApiException.builder()
        .status(errors.get(errorCode).getStatus())
        .message(errors.get(errorCode).getMessage())
        .build();
  }
}

package com.mycompany.users.domain.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserApiException extends RuntimeException {

  private int status;
  private String message;
}

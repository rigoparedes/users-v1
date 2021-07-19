package com.mycompany.users.domain.util;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserApplicationProperties {

  private List<ErrorProperties> errors;

  @Getter
  @Setter
  public static class ErrorProperties {

    private String code;
    private int status;
    private String message;
  }
}

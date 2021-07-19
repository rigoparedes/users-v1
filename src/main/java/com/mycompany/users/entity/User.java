package com.mycompany.users.entity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class User {

  private UUID id;
  private String name;
  private String email;
  private String password;
  private List<Phone> phones;
  private boolean active;
  private String token;
  private OffsetDateTime created;
  private OffsetDateTime modified;
  private OffsetDateTime lastLogin;
}

package com.mycompany.users.adapter.ouput.database.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserDb {

  @Id
  private UUID id;
  private String name;
  private String email;
  private String password;
  private boolean active;
  private String token;
  private OffsetDateTime created;
  private OffsetDateTime modified;
  private OffsetDateTime lastLogin;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<PhoneDb> phones = new ArrayList<>();
}

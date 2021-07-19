package com.mycompany.users.adapter.ouput.database.mapper;

import com.mycompany.users.adapter.ouput.database.model.UserDb;
import com.mycompany.users.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserDbMapper extends Converter<User, UserDb> {

  @AfterMapping
  default void fillUser(final User user, @MappingTarget UserDb userDb) {
    userDb.getPhones().forEach(it -> it.setUser(userDb));
  }
}

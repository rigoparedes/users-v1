package com.mycompany.users.adapter.input.web.mapper;

import com.mycompany.users.adapter.input.web.model.UserCreationRequest;
import com.mycompany.users.entity.User;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<UserCreationRequest, User> {

}

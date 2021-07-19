package com.mycompany.users.adapter.input.web.mapper;

import com.mycompany.users.adapter.input.web.model.UserCreationResponse;
import com.mycompany.users.entity.User;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserCreationResponseMapper extends Converter<User, UserCreationResponse> {

}

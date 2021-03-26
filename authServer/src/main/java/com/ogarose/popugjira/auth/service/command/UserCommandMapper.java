package com.ogarose.popugjira.auth.service.command;

import com.ogarose.popugjira.auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCommandMapper {
    @Mapping(target = "password", ignore = true)
    UserCommand toCommand(User user);

    User fromCommand(UserCommand userCommand);
}

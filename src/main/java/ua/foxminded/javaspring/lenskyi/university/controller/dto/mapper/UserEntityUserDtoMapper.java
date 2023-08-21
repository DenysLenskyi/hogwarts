package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.mapstruct.Mapper;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.model.User;

@Mapper(componentModel = "spring")
public interface UserEntityUserDtoMapper {
    UserDto userEntityToUserDto(User user);
    User userDtoToUserEntity(UserDto userDto);
}
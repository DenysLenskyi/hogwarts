package ua.foxminded.javaspring.lenskyi.university.controller.dto.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.javaspring.lenskyi.university.controller.dto.UserDto;
import ua.foxminded.javaspring.lenskyi.university.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class UserEntityUserDtoMapperTest {

    @Autowired
    private UserEntityUserDtoMapper mapper;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void mapperTest() {
        UserDto severusDto = mapper.userEntityToUserDto(userServiceImpl.findUserByUsername("severussnape"));
        assertEquals("Severus", severusDto.getFirstName());
    }
}
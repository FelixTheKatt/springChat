package io.github.felix.chatspringtest.mapper;

import io.github.felix.chatspringtest.dto.UserDto;
import io.github.felix.chatspringtest.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {
    @Override
    public UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .pseudo(user.getPseudo())
                .active(user.isActive())
                .telephoneNumber(user.getTelephoneNumber())
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) return null;
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPseudo(userDto.getPseudo());
        user.setActive(userDto.isActive());
        user.setTelephoneNumber(userDto.getTelephoneNumber());
        return user;
    }
}

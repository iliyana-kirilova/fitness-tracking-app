package app.mapper.user;

import app.models.dto.user.UserDto;
import app.models.dto.user.UserRegisterRequest;
import app.models.entity.user.User;
import app.models.entity.user.UserRole;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class UserMapper {
    public static User toUserEntity(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        return User.builder()
                .username(userRegisterRequest.getUsername())
                .password(userRegisterRequest.getPassword())
                .email(userRegisterRequest.getEmail())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .country(userRegisterRequest.getCountry())
                .role(UserRole.USER)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .country(user.getCountry())
                .role(user.getRole())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();

    }

}

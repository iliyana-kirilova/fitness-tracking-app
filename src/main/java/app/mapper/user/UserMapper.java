package app.mapper.user;

import app.mapper.dailyLog.DailyLogMapper;
import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.user.UserDto;
import app.models.dto.user.UserRegisterRequest;
import app.models.entity.user.User;
import app.models.entity.user.UserRole;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
                .isActive(true)
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
                .profilePicture(user.getProfilePicture())
                .country(user.getCountry())
                .role(user.getRole())
                .isActive(user.isActive())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .build();

    }

}

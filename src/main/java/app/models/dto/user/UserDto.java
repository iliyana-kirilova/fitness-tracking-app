package app.models.dto.user;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.entity.dailyLog.DailyLog;
import app.models.entity.user.Country;
import app.models.entity.user.UserRole;
import app.models.entity.userProfile.UserProfile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String profilePicture;
    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
    private Country country;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private UserProfile userProfile;
    private List<DailyLogDto> dailyLogs;
}
